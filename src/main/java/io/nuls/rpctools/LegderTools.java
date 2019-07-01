package io.nuls.rpctools;

import io.nuls.base.api.provider.Result;
import io.nuls.core.core.annotation.Component;
import io.nuls.core.exception.NulsException;
import io.nuls.core.exception.NulsRuntimeException;
import io.nuls.core.rpc.info.Constants;
import io.nuls.core.rpc.model.ModuleE;
import io.nuls.rpctools.vo.AccountBalance;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author: zhoulijun
 * @Time: 2019-06-12 17:31
 * @Description: 账本模块工具类
 */
@Component
public class LegderTools implements CallRpc {

    /**
     * 获取可用余额和nonce
     * Get the available balance and nonce
     */
    public Result<AccountBalance> getBalanceAndNonce(int chainId, String address, int assetChainId, int assetId) {
        Map<String, Object> params = new HashMap(4);
        params.put(Constants.CHAIN_ID, chainId);
        params.put("assetChainId", assetChainId);
        params.put("address", address);
        params.put("assetId", assetId);
        try {
            return callRpc(ModuleE.LG.abbr, "getBalanceNonce", params, (Function<Map<String, Object>, Result<AccountBalance>>) map -> {
                if (map == null) {
                    return null;
                }
                AccountBalance balanceInfo = new AccountBalance();
                balanceInfo.setBalance(new BigInteger(map.get("available").toString()));
                balanceInfo.setTimeLock(new BigInteger(map.get("timeHeightLocked").toString()));
                balanceInfo.setConsensusLock(new BigInteger(map.get("permanentLocked").toString()));
                balanceInfo.setFreeze(new BigInteger(map.get("freeze").toString()));
                balanceInfo.setNonce((String) map.get("nonce"));
                balanceInfo.setTotalBalance(balanceInfo.getBalance().add(balanceInfo.getConsensusLock()).add(balanceInfo.getTimeLock()));
                balanceInfo.setNonceType((Integer) map.get("nonceType"));
                return new Result<>(balanceInfo);
            });
        } catch (NulsRuntimeException e) {
            return Result.fail(e.getCode(), e.getMessage());
        }
    }


}
