package io.nuls.rpctools;

import io.nuls.api.constant.CommandConstant;
import io.nuls.base.api.provider.Result;
import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.data.Block;
import io.nuls.core.core.annotation.Component;
import io.nuls.core.crypto.HexUtil;
import io.nuls.core.exception.NulsException;
import io.nuls.core.exception.NulsRuntimeException;
import io.nuls.core.parse.MapUtils;
import io.nuls.core.rpc.info.Constants;
import io.nuls.core.rpc.model.ModuleE;
import io.nuls.core.rpc.util.RpcCall;
import io.nuls.rpctools.vo.Account;
import io.nuls.utils.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author: zhoulijun
 * @Time: 2019-06-12 14:06
 * @Description: 区块模块工具类
 */
@Component
public class BlockTools implements CallRpc {

    /**
     * 根据高度获取区块
     *
     * @param chainId
     * @param height
     * @return
     */
    public Result<Block> getBlockByHeight(int chainId, long height) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("chainId", chainId);
        param.put("height", height);
        try {
            Block block = callRpc(ModuleE.BL.name, "getBlockByHeight", param, (Function<String, Block>) res -> {
                if (res == null) {
                    return null;
                }
                Block _block = new Block();
                try {
                    _block.parse(new NulsByteBuffer(HexUtil.decode(res)));
                } catch (NulsException e) {
                    Log.error(e);
                    return null;
                }
                return _block;
            });
            return new Result(block);
        } catch (NulsRuntimeException e) {
            return Result.fail(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据hash获取区块
     *
     * @param chainId
     * @param hash
     * @return
     */
    public Result<Block> getBlockByHash(int chainId, String hash) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("chainId", chainId);
        param.put("hash", hash);
        try {
            Block block = callRpc(ModuleE.BL.name, "downloadBlockByHash", param, (Function<String, Block>) res -> {
                if (res == null) {
                    return null;
                }
                Block _block = new Block();
                try {
                    _block.parse(new NulsByteBuffer(HexUtil.decode(res)));
                } catch (NulsException e) {
                    Log.error(e);
                    return null;
                }
                return _block;
            });
            return new Result(block);
        } catch (NulsRuntimeException e) {
            return Result.fail(e.getCode(), e.getMessage());
        }
    }

    /**
     * 获取最新区块
     *
     * @param chainId
     * @return
     */
    public Result<Block> getBestBlock(int chainId) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("chainId", chainId);
        try {
            Block block = callRpc(ModuleE.BL.name, "latestBlock", param, (Function<String, Block>) res -> {
                if (res == null) {
                    return null;
                }
                Block _block = new Block();
                try {
                    _block.parse(new NulsByteBuffer(HexUtil.decode(res)));
                } catch (NulsException e) {
                    Log.error(e);
                    return null;
                }
                return _block;
            });
            return new Result(block);
        } catch (NulsRuntimeException e) {
            return Result.fail(e.getCode(), e.getMessage());
        }
    }

    public Result<Map> getInfo(int chainId) {
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.CHAIN_ID, chainId);
        try {
            Map map = (Map) RpcCall.request(ModuleE.CS.abbr, CommandConstant.GET_CONSENSUS_CONFIG, params);
            return new Result<>(map);
        } catch (NulsException e) {
            return Result.fail(e.getErrorCode().getCode(), e.getMessage());
        }
    }
}
