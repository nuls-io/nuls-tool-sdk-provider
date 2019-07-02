/*
 * MIT License
 * Copyright (c) 2017-2019 nuls.io
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.nuls.api.jsonrpc.controller;

import io.nuls.api.config.Context;
import io.nuls.base.api.provider.Result;
import io.nuls.base.basic.AddressTool;
import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Controller;
import io.nuls.core.core.annotation.RpcMethod;
import io.nuls.core.exception.NulsException;
import io.nuls.core.model.StringUtils;
import io.nuls.model.dto.ContractInfoDto;
import io.nuls.model.dto.ContractResultDto;
import io.nuls.model.dto.ProgramMethod;
import io.nuls.model.dto.ProgramMethodArg;
import io.nuls.model.jsonrpc.RpcErrorCode;
import io.nuls.model.jsonrpc.RpcResult;
import io.nuls.model.jsonrpc.RpcResultError;
import io.nuls.rpctools.ContractTools;
import io.nuls.utils.VerifyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: PierreLuo
 * @date: 2019-07-01
 */
@Controller
public class ContractController {

    @Autowired
    private ContractTools contractTools;

    @RpcMethod("getContract")
    public RpcResult getContract(List<Object> params) {
        VerifyUtils.verifyParams(params, 2);
        int chainId;
        String contractAddress;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        try {
            contractAddress = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[contractAddress] is invalid");
        }


        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return RpcResult.paramError("[contractAddress] is invalid");
        }

        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<ContractInfoDto> contractInfoDtoResult = contractTools.getContractInfo(chainId, contractAddress);
        if (contractInfoDtoResult.isFailed()) {
            return rpcResult.setError(new RpcResultError(contractInfoDtoResult.getStatus(), contractInfoDtoResult.getMessage(), null));
        }
        ContractInfoDto contractInfo = contractInfoDtoResult.getData();
        rpcResult.setResult(contractInfo);
        return rpcResult;
    }

    @RpcMethod("getContractTxResult")
    public RpcResult getContractResult(List<Object> params) {
        VerifyUtils.verifyParams(params, 2);
        int chainId;
        String hash;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        try {
            hash = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[txHash] is invalid");
        }

        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<ContractResultDto> contractResult = contractTools.getContractResult(chainId, hash);
        if (contractResult.isFailed()) {
            return rpcResult.setError(new RpcResultError(contractResult.getStatus(), contractResult.getMessage(), null));
        }
        ContractResultDto dto = contractResult.getData();
        rpcResult.setResult(dto);
        return rpcResult;
    }

    /**
     * 获取合约代码构造函数
     */
    @RpcMethod("getContractConstructor")
    public RpcResult getContractConstructor(List<Object> params) throws NulsException {
        VerifyUtils.verifyParams(params, 2);
        int chainId;
        String contractCode;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        try {
            contractCode = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[contractCode] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> mapResult = contractTools.getContractConstructor(chainId, contractCode);
        if (mapResult.isFailed()) {
            return rpcResult.setError(new RpcResultError(mapResult.getStatus(), mapResult.getMessage(), null));
        }
        Map resultData = mapResult.getData();
        if (resultData == null) {
            rpcResult.setError(new RpcResultError(RpcErrorCode.DATA_NOT_EXISTS));
        } else {
            rpcResult.setResult(resultData);
        }
        return rpcResult;
    }

    /**
     * 获取合约方法信息
     *
     * @param params
     * @return
     */
    @RpcMethod("getContractMethod")
    public RpcResult getContractMethod(List<Object> params) {
        VerifyUtils.verifyParams(params, 3);
        int chainId = (int) params.get(0);
        String contractAddress = (String) params.get(1);
        String methodName = (String) params.get(2);
        String methodDesc = null;
        if (params.size() > 3) {
            methodDesc = (String) params.get(3);
        }

        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return RpcResult.paramError("[contractAddress] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        if (StringUtils.isBlank(methodName)) {
            return RpcResult.paramError("[methodName] is invalid");
        }
        RpcResult rpcResult = new RpcResult();
        Result<ContractInfoDto> contractInfoDtoResult = contractTools.getContractInfo(chainId, contractAddress);
        if (contractInfoDtoResult.isFailed()) {
            return rpcResult.setError(new RpcResultError(contractInfoDtoResult.getStatus(), contractInfoDtoResult.getMessage(), null));
        }
        ContractInfoDto contractInfo = contractInfoDtoResult.getData();
        List<ProgramMethod> methods = contractInfo.getMethod();
        ProgramMethod resultMethod = null;
        boolean isEmptyMethodDesc = StringUtils.isBlank(methodDesc);
        for (ProgramMethod method : methods) {
            if (method.getName().equals(methodName)) {
                if (isEmptyMethodDesc) {
                    resultMethod = method;
                    break;
                } else if (methodDesc.equals(method.getDesc())) {
                    resultMethod = method;
                    break;
                }
            }
        }
        if (resultMethod == null) {
            return RpcResult.dataNotFound();
        }
        rpcResult.setResult(resultMethod);
        return rpcResult;
    }

    /**
     * 获取合约方法参数类型
     */
    @RpcMethod("getContractMethodArgsTypes")
    public RpcResult getContractMethodArgsTypes(List<Object> params) {
        VerifyUtils.verifyParams(params, 3);
        int chainId;
        String contractAddress;
        String methodName;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        try {
            contractAddress = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[contractAddress] is invalid");
        }
        try {
            methodName = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[methodName] is invalid");
        }
        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return RpcResult.paramError("[contractAddress] is invalid");
        }

        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<ContractInfoDto> contractInfoDtoResult = contractTools.getContractInfo(chainId, contractAddress);
        if (contractInfoDtoResult.isFailed()) {
            return rpcResult.setError(new RpcResultError(contractInfoDtoResult.getStatus(), contractInfoDtoResult.getMessage(), null));
        }
        ContractInfoDto contractInfo = contractInfoDtoResult.getData();
        List<ProgramMethod> methods = contractInfo.getMethod();
        List<String> argsTypes = null;
        for (ProgramMethod method : methods) {
            if (method.getName().equals(methodName)) {
                List<ProgramMethodArg> args = method.getArgs();
                argsTypes = new ArrayList<>();
                for (ProgramMethodArg arg : args) {
                    argsTypes.add(arg.getType());
                }
                break;
            }
        }
        if (argsTypes == null) {
            return RpcResult.dataNotFound();
        }
        rpcResult.setResult(argsTypes);
        return rpcResult;
    }

    /**
     * 验证创建合约
     *
     * @param params
     * @return
     */
    @RpcMethod("validateContractCreate")
    public RpcResult validateContractCreate(List<Object> params) throws NulsException {
        VerifyUtils.verifyParams(params, 6);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> mapResult = contractTools.validateContractCreate(chainId,
                params.get(1),
                params.get(2),
                params.get(3),
                params.get(4),
                params.get(5)
        );
        rpcResult.setResult(mapResult.getData());
        return rpcResult;
    }

    /**
     * 验证调用合约
     *
     * @param params
     * @return
     */
    @RpcMethod("validateContractCall")
    public RpcResult validateContractCall(List<Object> params) throws NulsException {
        VerifyUtils.verifyParams(params, 9);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> mapResult = contractTools.validateContractCall(chainId,
                params.get(1),
                params.get(2),
                params.get(3),
                params.get(4),
                params.get(5),
                params.get(6),
                params.get(7),
                params.get(8)
        );
        rpcResult.setResult(mapResult.getData());
        return rpcResult;
    }

    /**
     * 验证删除合约
     *
     * @param params
     * @return
     */
    @RpcMethod("validateContractDelete")
    public RpcResult validateContractDelete(List<Object> params) throws NulsException {
        VerifyUtils.verifyParams(params, 3);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> mapResult = contractTools.validateContractDelete(chainId,
                params.get(1),
                params.get(2)
        );
        rpcResult.setResult(mapResult.getData());
        return rpcResult;
    }

    /**
     * 预估创建合约交易的gas
     *
     * @param params
     * @return
     */
    @RpcMethod("imputedContractCreateGas")
    public RpcResult imputedContractCreateGas(List<Object> params) throws NulsException {
        VerifyUtils.verifyParams(params, 4);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> mapResult = contractTools.imputedContractCreateGas(chainId,
                params.get(1),
                params.get(2),
                params.get(3)
        );
        rpcResult.setResult(mapResult.getData());
        return rpcResult;
    }

    /**
     * 预估调用合约交易的gas
     *
     * @param params
     * @return
     */
    @RpcMethod("imputedContractCallGas")
    public RpcResult imputedContractCallGas(List<Object> params) throws NulsException {
        VerifyUtils.verifyParams(params, 7);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> mapResult = contractTools.imputedContractCallGas(chainId,
                params.get(1),
                params.get(2),
                params.get(3),
                params.get(4),
                params.get(5),
                params.get(6)
        );
        rpcResult.setResult(mapResult.getData());
        return rpcResult;
    }

    /**
     * 调用合约不上链方法
     *
     * @param params
     * @return
     */
    @RpcMethod("invokeView")
    public RpcResult invokeView(List<Object> params) throws NulsException {
        VerifyUtils.verifyParams(params, 5);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> mapResult = contractTools.invokeView(chainId,
                params.get(1),
                params.get(2),
                params.get(3),
                params.get(4)
        );
        rpcResult.setResult(mapResult.getData());
        return rpcResult;
    }

}
