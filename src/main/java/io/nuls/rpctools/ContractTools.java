package io.nuls.rpctools;

import io.nuls.base.api.provider.Result;
import io.nuls.core.core.annotation.Component;
import io.nuls.core.exception.NulsException;
import io.nuls.core.exception.NulsRuntimeException;
import io.nuls.core.parse.MapUtils;
import io.nuls.core.rpc.info.Constants;
import io.nuls.core.rpc.model.ModuleE;
import io.nuls.model.dto.ContractInfoDto;
import io.nuls.model.dto.ContractResultDto;
import io.nuls.model.dto.ContractTokenInfoDto;
import io.nuls.rpctools.vo.AccountBalance;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author: PierreLuo
 * @date: 2019-06-30
 */
@Component
public class ContractTools implements CallRpc {

    public Result<ContractTokenInfoDto> getTokenBalance(int chainId, String contractAddress, String address) {
        Map<String, Object> params = new HashMap(4);
        params.put(Constants.CHAIN_ID, chainId);
        params.put("contractAddress", contractAddress);
        params.put("address", address);
        try {
            return  callRpc(ModuleE.SC.abbr, "sc_token_balance", params,(Function<Map<String,Object>, Result<ContractTokenInfoDto>>)  res->{
                if(res == null){
                    return null;
                }
                return new Result(MapUtils.mapToBean(res, new ContractTokenInfoDto()));
            });
        } catch (NulsRuntimeException e) {
            return Result.fail(e.getCode(), e.getMessage());
        }
    }

    public Result<ContractInfoDto> getContractInfo(int chainId, String contractAddress) {
        Map<String, Object> params = new HashMap(4);
        params.put(Constants.CHAIN_ID, chainId);
        params.put("contractAddress", contractAddress);
        try {
            return  callRpc(ModuleE.SC.abbr, "sc_contract_info", params,(Function<Map<String,Object>, Result<ContractInfoDto>>)  res->{
                if(res == null){
                    return null;
                }
                return new Result(MapUtils.mapToBean(res, new ContractInfoDto()));
            });
        } catch (NulsRuntimeException e) {
            return Result.fail(e.getCode(), e.getMessage());
        }
    }

    public Result<ContractResultDto> getContractResult(int chainId, String hash) {
        Map<String, Object> params = new HashMap(4);
        params.put(Constants.CHAIN_ID, chainId);
        params.put("hash", hash);
        try {
            return  callRpc(ModuleE.SC.abbr, "sc_contract_result", params,(Function<Map<String,Object>, Result<ContractResultDto>>) res->{
                if(res == null){
                    return null;
                }
                return new Result(MapUtils.mapToBean(res, new ContractResultDto()));
            });
        } catch (NulsRuntimeException e) {
            return Result.fail(e.getCode(), e.getMessage());
        }
    }


}
