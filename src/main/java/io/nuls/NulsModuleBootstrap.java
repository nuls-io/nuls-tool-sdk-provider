package io.nuls;

import io.nuls.api.RpcServerManager;
import io.nuls.base.api.provider.Provider;
import io.nuls.base.api.provider.ServiceManager;
import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Service;
import io.nuls.core.core.annotation.Value;
import io.nuls.core.core.config.ConfigurationLoader;
import io.nuls.core.rpc.info.HostInfo;
import io.nuls.core.rpc.model.ModuleE;
import io.nuls.core.rpc.modulebootstrap.Module;
import io.nuls.core.rpc.modulebootstrap.NulsRpcModuleBootstrap;
import io.nuls.core.rpc.modulebootstrap.RpcModule;
import io.nuls.core.rpc.modulebootstrap.RpcModuleState;
import io.nuls.v2.NulsSDKBootStrap;
import io.nuls.v2.SDKContext;

import java.util.Map;

import static io.nuls.api.constant.SdkConstant.SDK_PROVIDER_DOMAIN;

/**
 * @Author: zhoulijun
 * @Time: 2019-06-10 19:27
 * @Description: 模块启动类
 */
@Service
public abstract class NulsModuleBootstrap extends RpcModule {

    @Value("APP_NAME")
    private String moduleName;

    @Autowired
    MyModule myModule;

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            args = new String[]{"ws://" + HostInfo.getLocalIP() + ":7771"};
        }
        ConfigurationLoader configurationLoader = new ConfigurationLoader();
        configurationLoader.load();
        Provider.ProviderType providerType = Provider.ProviderType.valueOf(configurationLoader.getValue("providerType"));
        int defaultChainId = Integer.parseInt(configurationLoader.getValue("chainId"));
        ServiceManager.init(defaultChainId, providerType);
        NulsRpcModuleBootstrap.run("io.nuls", args);

        initRpcServer(configurationLoader);
        NulsSDKBootStrap.init(defaultChainId, null);
    }

    private static void initRpcServer(ConfigurationLoader configurationLoader) {
        String server_ip = "0.0.0.0";
        int server_port = 9898;
        Map<String, ConfigurationLoader.ConfigItem> configItemMap = configurationLoader.getConfigData().get(SDK_PROVIDER_DOMAIN);
        if (configItemMap != null) {
            ConfigurationLoader.ConfigItem serverIp = configItemMap.get("server_ip");
            if (serverIp != null) {
                server_ip = serverIp.getValue();
            }
            ConfigurationLoader.ConfigItem serverPort = configItemMap.get("server_port");
            if (serverPort != null) {
                server_port = Integer.parseInt(serverPort.getValue());
            }
        }
        RpcServerManager.getInstance().startServer(server_ip, server_port);
    }


    @Override
    public Module[] declareDependent() {
        return new Module[]{
                new Module(ModuleE.CS.abbr, ROLE),
                new Module(ModuleE.BL.abbr, ROLE),
                new Module(ModuleE.AC.abbr, ROLE),
                new Module(ModuleE.TX.abbr, ROLE),
                new Module(ModuleE.LG.abbr, ROLE)
        };
    }

    @Override
    public Module moduleInfo() {
        return new Module(moduleName, ROLE);
    }

    @Override
    public boolean doStart() {
        return true;
    }

    @Override
    public RpcModuleState onDependenciesReady() {
        return myModule.startModule(moduleName);
    }

    @Override
    public RpcModuleState onDependenciesLoss(Module dependenciesModule) {
        return RpcModuleState.Running;
    }

}
