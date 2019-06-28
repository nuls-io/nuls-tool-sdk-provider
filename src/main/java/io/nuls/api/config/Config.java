package io.nuls.api.config;

import io.nuls.base.api.provider.Provider;
import io.nuls.core.basic.InitializingBean;
import io.nuls.core.core.annotation.Configuration;
import io.nuls.core.core.annotation.Value;
import io.nuls.core.exception.NulsException;

/**
 * @Author: zhoulijun
 * @Time: 2019-03-07 16:56
 * @Description:
 */
@Configuration(domain = "sdk_provider")
public class Config implements InitializingBean {

    private Integer mainChainId;

    @Value.NotNull
    private Integer chainId;

    @Value.NotNull
    private Integer assetsId;

    private String language;

    public boolean isMainChain() {
        return chainId.equals(mainChainId);
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public Integer getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Integer assetsId) {
        this.assetsId = assetsId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getMainChainId() {
        return mainChainId;
    }

    public void setMainChainId(Integer mainChainId) {
        this.mainChainId = mainChainId;
    }

    @Override
    public void afterPropertiesSet() throws NulsException {
    }
}
