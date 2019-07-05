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

import io.nuls.api.config.Config;
import io.nuls.api.config.Context;
import io.nuls.base.api.provider.Result;
import io.nuls.base.api.provider.ServiceManager;
import io.nuls.base.api.provider.contract.ContractProvider;
import io.nuls.base.api.provider.contract.facade.*;
import io.nuls.base.basic.AddressTool;
import io.nuls.core.constant.CommonCodeConstanst;
import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Controller;
import io.nuls.core.core.annotation.RpcMethod;
import io.nuls.core.model.FormatValidUtils;
import io.nuls.core.model.StringUtils;
import io.nuls.core.rpc.model.*;
import io.nuls.model.dto.ContractConstructorInfoDto;
import io.nuls.model.dto.ContractInfoDto;
import io.nuls.model.dto.ContractResultDto;
import io.nuls.model.dto.ProgramMethod;
import io.nuls.model.jsonrpc.RpcErrorCode;
import io.nuls.model.jsonrpc.RpcResult;
import io.nuls.model.jsonrpc.RpcResultError;
import io.nuls.rpctools.ContractTools;
import io.nuls.utils.Log;
import io.nuls.utils.ResultUtil;
import io.nuls.utils.VerifyUtils;
import io.nuls.v2.model.annotation.Api;
import io.nuls.v2.model.annotation.ApiOperation;
import io.nuls.v2.model.annotation.ApiType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: PierreLuo
 * @date: 2019-07-01
 */
@Controller
@Api(type = ApiType.JSONRPC)
public class ContractController {

    ContractProvider contractProvider = ServiceManager.get(ContractProvider.class);
    @Autowired
    Config config;
    @Autowired
    private ContractTools contractTools;


    @RpcMethod("getContract")
    @ApiOperation(description = "获取智能合约详细信息")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链ID"),
        @Parameter(parameterName = "contractAddress", parameterDes = "合约地址")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = ContractInfoDto.class))
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
            return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> contractInfoDtoResult = contractTools.getContractInfo(chainId, contractAddress);
        if (contractInfoDtoResult.isFailed()) {
            return rpcResult.setError(new RpcResultError(contractInfoDtoResult.getStatus(), contractInfoDtoResult.getMessage(), null));
        }
        rpcResult.setResult(contractInfoDtoResult.getData());
        return rpcResult;
    }

    @RpcMethod("getContractTxResult")
    @ApiOperation(description = "获取智能合约执行结果")
    @Parameters({
        @Parameter(parameterName = "hash", parameterDes = "交易hash")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = ContractResultDto.class))
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
            return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> contractResult = contractTools.getContractResult(chainId, hash);
        if (contractResult.isFailed()) {
            return rpcResult.setError(new RpcResultError(contractResult.getStatus(), contractResult.getMessage(), null));
        }
        Map dto = contractResult.getData();
        rpcResult.setResult(dto);
        return rpcResult;
    }

    @RpcMethod("getContractConstructor")
    @ApiOperation(description = "获取合约代码构造函数")
    @Parameters({
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链ID"),
        @Parameter(parameterName = "contractCode", parameterDes = "智能合约代码(字节码的Hex编码字符串)")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = ContractConstructorInfoDto.class))
    public RpcResult getContractConstructor(List<Object> params) {
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
            return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
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

    @RpcMethod("getContractMethod")
    @ApiOperation(description = "获取合约方法信息")
    @Parameters({
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链ID"),
        @Parameter(parameterName = "contractAddress", parameterDes = "合约地址"),
        @Parameter(parameterName = "methodName", parameterDes = "方法名称"),
        @Parameter(parameterName = "methodDesc", parameterDes = "方法描述", canNull = true)
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = ProgramMethod.class))
    public RpcResult getContractMethod(List<Object> params) {
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
        String methodDesc = null;
        if (params.size() > 3) {
            methodDesc = (String) params.get(3);
        }

        if (!AddressTool.validAddress(chainId, contractAddress)) {
            return RpcResult.paramError("[contractAddress] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
        }
        if (StringUtils.isBlank(methodName)) {
            return RpcResult.paramError("[methodName] is invalid");
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> contractInfoDtoResult = contractTools.getContractInfo(chainId, contractAddress);
        if (contractInfoDtoResult.isFailed()) {
            return rpcResult.setError(new RpcResultError(contractInfoDtoResult.getStatus(), contractInfoDtoResult.getMessage(), null));
        }
        Map contractInfo = contractInfoDtoResult.getData();
        try {
            List<Map<String, Object>> methods =(List<Map<String, Object>>) contractInfo.get("method");
            Map resultMethod = null;
            boolean isEmptyMethodDesc = StringUtils.isBlank(methodDesc);
            for (Map<String, Object> method : methods) {
                if (methodName.equals(method.get("name"))) {
                    if (isEmptyMethodDesc) {
                        resultMethod = method;
                        break;
                    } else if (methodDesc.equals(method.get("desc"))) {
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
        } catch (Exception e) {
            Log.error(e);
            return RpcResult.failed(CommonCodeConstanst.DATA_ERROR, e.getMessage());
        }
    }

    @RpcMethod("getContractMethodArgsTypes")
    @ApiOperation(description = "获取合约方法参数类型")
    @Parameters({
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链ID"),
        @Parameter(parameterName = "contractAddress", parameterDes = "合约地址"),
        @Parameter(parameterName = "methodName", parameterDes = "方法名称"),
        @Parameter(parameterName = "methodDesc", parameterDes = "方法描述", canNull = true)
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = List.class, collectionElement = String.class))
    public RpcResult getContractMethodArgsTypes(List<Object> params) {
        RpcResult result = this.getContractMethod(params);
        if(result.getError() != null) {
            return result;
        }
        Map resultMethod = (Map) result.getResult();
        if (resultMethod == null) {
            return RpcResult.dataNotFound();
        }
        List<String> argsTypes;
        try {
            List<Map<String, Object>> args = (List<Map<String, Object>>) resultMethod.get("args");
            argsTypes = new ArrayList<>();
            for (Map<String, Object> arg : args) {
                argsTypes.add((String) arg.get("type"));
            }
            RpcResult rpcResult = new RpcResult();
            rpcResult.setResult(argsTypes);
            return rpcResult;
        } catch (Exception e) {
            Log.error(e);
            return RpcResult.failed(CommonCodeConstanst.DATA_ERROR, e.getMessage());
        }
    }

    @RpcMethod("contractCreate")
    @ApiOperation(description = "发布合约")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "sender",  parameterDes = "交易创建者账户地址"),
        @Parameter(parameterName = "password",  parameterDes = "账户密码"),
        @Parameter(parameterName = "alias",  parameterDes = "合约别名"),
        @Parameter(parameterName = "gasLimit", requestType = @TypeDescriptor(value = long.class), parameterDes = "GAS限制"),
        @Parameter(parameterName = "price", requestType = @TypeDescriptor(value = long.class), parameterDes = "GAS单价"),
        @Parameter(parameterName = "contractCode",  parameterDes = "智能合约代码(字节码的Hex编码字符串)"),
        @Parameter(parameterName = "args", requestType = @TypeDescriptor(value = Object[].class), parameterDes = "参数列表", canNull = true),
        @Parameter(parameterName = "remark",  parameterDes = "交易备注", canNull = true)
    })
    @ResponseData(name = "返回值", description = "返回一个Map对象，包含两个属性", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "txHash", description = "发布合约的交易hash"),
        @Key(name = "contractAddress", description = "生成的合约地址")
    }))
    public RpcResult contractCreate(List<Object> params) {
        try {
            int i = 0;
            Integer chainId = (Integer) params.get(i++);
            String sender = (String) params.get(i++);
            String password = (String) params.get(i++);
            String alias = (String) params.get(i++);
            Long gasLimit = Long.parseLong(params.get(i++).toString());
            Long price = Long.parseLong(params.get(i++).toString());
            String contractCode = (String) params.get(i++);
            List argsList = (List) params.get(i++);
            Object[] args = argsList != null ? argsList.toArray() : null;
            String remark = (String) params.get(i++);

            if (gasLimit < 0) {
                return RpcResult.paramError(String.format("gasLimit [%s] is invalid", gasLimit));
            }
            if (price < 0) {
                return RpcResult.paramError(String.format("price [%s] is invalid", price));
            }

            if (!AddressTool.validAddress(chainId, sender)) {
                return RpcResult.paramError(String.format("sender [%s] is invalid", sender));
            }

            if(!FormatValidUtils.validAlias(alias)) {
                return RpcResult.paramError(String.format("alias [%s] is invalid", alias));
            }

            if (StringUtils.isBlank(contractCode)) {
                return RpcResult.paramError("contractCode is empty");
            }
            CreateContractReq req = new CreateContractReq();
            req.setChainId(config.getChainId());
            req.setSender(sender);
            req.setPassword(password);
            req.setPrice(price);
            req.setGasLimit(gasLimit);
            req.setContractCode(contractCode);
            req.setAlias(alias);
            req.setArgs(args);
            req.setRemark(remark);
            Result<Map> result = contractProvider.createContract(req);
            return ResultUtil.getJsonRpcResult(result);
        } catch (Exception e) {
            Log.error(e);
            return RpcResult.failed(CommonCodeConstanst.DATA_ERROR, e.getMessage());
        }
    }

    @RpcMethod("contractCall")
    @ApiOperation(description = "调用合约")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "sender",  parameterDes = "交易创建者账户地址"),
        @Parameter(parameterName = "password",  parameterDes = "调用者账户密码"),
        @Parameter(parameterName = "value", requestType = @TypeDescriptor(value = BigInteger.class), parameterDes = "调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO"),
        @Parameter(parameterName = "gasLimit", requestType = @TypeDescriptor(value = long.class), parameterDes = "GAS限制"),
        @Parameter(parameterName = "price", requestType = @TypeDescriptor(value = long.class), parameterDes = "GAS单价"),
        @Parameter(parameterName = "contractAddress",  parameterDes = "合约地址"),
        @Parameter(parameterName = "methodName",  parameterDes = "合约方法"),
        @Parameter(parameterName = "methodDesc",  parameterDes = "合约方法描述，若合约内方法没有重载，则此参数可以为空", canNull = true),
        @Parameter(parameterName = "args", requestType = @TypeDescriptor(value = Object[].class), parameterDes = "参数列表", canNull = true),
        @Parameter(parameterName = "remark",  parameterDes = "交易备注", canNull = true)
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "txHash", description = "调用合约的交易hash")
    }))
    public RpcResult contractCall(List<Object> params) {
        try {
            int i = 0;
            Integer chainId = (Integer) params.get(i++);
            String sender = (String) params.get(i++);
            String password = (String) params.get(i++);
            Object valueObj = params.get(i++);
            if(valueObj == null) {
                return RpcResult.paramError("value is empty");
            }
            BigInteger value = new BigInteger(valueObj.toString());
            if (value.compareTo(BigInteger.ZERO) < 0) {
                return RpcResult.paramError(String.format("value [%s] is invalid", value.toString()));
            }
            Long gasLimit = Long.parseLong(params.get(i++).toString());
            Long price = Long.parseLong(params.get(i++).toString());
            String contractAddress = (String) params.get(i++);
            String methodName = (String) params.get(i++);
            String methodDesc = (String) params.get(i++);
            List argsList = (List) params.get(i++);
            Object[] args = argsList != null ? argsList.toArray() : null;
            String remark = (String) params.get(i++);


            if (gasLimit < 0) {
                return RpcResult.paramError(String.format("gasLimit [%s] is invalid", gasLimit));
            }
            if (price < 0) {
                return RpcResult.paramError(String.format("price [%s] is invalid", price));
            }
            if (!AddressTool.validAddress(chainId, sender)) {
                return RpcResult.paramError(String.format("sender [%s] is invalid", sender));
            }
            if (!AddressTool.validAddress(chainId, contractAddress)) {
                return RpcResult.paramError(String.format("contractAddress [%s] is invalid", contractAddress));
            }
            if (StringUtils.isBlank(methodName)) {
                return RpcResult.paramError("methodName is empty");
            }

            CallContractReq req = new CallContractReq();
            req.setChainId(config.getChainId());
            req.setSender(sender);
            req.setPassword(password);
            req.setPrice(price);
            req.setGasLimit(gasLimit);
            req.setValue(value.longValue());
            req.setMethodName(methodName);
            req.setMethodDesc(methodDesc);
            req.setContractAddress(contractAddress);
            req.setArgs(args);
            req.setRemark(remark);
            Result<String> result = contractProvider.callContract(req);
            RpcResult rpcResult = ResultUtil.getJsonRpcResult(result);
            if(rpcResult.getError() == null) {
                Map dataMap = new HashMap();
                dataMap.put("txHash", rpcResult.getResult());
                rpcResult.setResult(dataMap);
            }
            return rpcResult;
        } catch (Exception e) {
            Log.error(e);
            return RpcResult.failed(CommonCodeConstanst.DATA_ERROR, e.getMessage());
        }
    }


    @RpcMethod("contractDelete")
    @ApiOperation(description = "删除合约")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "sender", parameterDes = "交易创建者账户地址"),
        @Parameter(parameterName = "password", parameterDes = "交易账户密码"),
        @Parameter(parameterName = "contractAddress", parameterDes = "合约地址"),
        @Parameter(parameterName = "remark", parameterDes = "交易备注", canNull = true)
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "txHash", description = "删除合约的交易hash")
    }))
    public RpcResult contractDelete(List<Object> params) {
        try {
            int i = 0;
            Integer chainId = (Integer) params.get(i++);
            String sender = (String) params.get(i++);
            String password = (String) params.get(i++);
            String contractAddress = (String) params.get(i++);
            String remark = (String) params.get(i++);
            if (!AddressTool.validAddress(chainId, sender)) {
                return RpcResult.paramError(String.format("sender [%s] is invalid", sender));
            }
            if (!AddressTool.validAddress(chainId, contractAddress)) {
                return RpcResult.paramError(String.format("contractAddress [%s] is invalid", contractAddress));
            }
            DeleteContractReq req = new DeleteContractReq(sender, contractAddress, password);
            req.setChainId(config.getChainId());
            req.setRemark(remark);
            Result<String> result = contractProvider.deleteContract(req);
            RpcResult rpcResult = ResultUtil.getJsonRpcResult(result);
            if(rpcResult.getError() == null) {
                Map dataMap = new HashMap();
                dataMap.put("txHash", rpcResult.getResult());
                rpcResult.setResult(dataMap);
            }
            return rpcResult;
        } catch (Exception e) {
            Log.error(e);
            return RpcResult.failed(CommonCodeConstanst.DATA_ERROR, e.getMessage());
        }
    }


    @RpcMethod("tokentransfer")
    @ApiOperation(description = "token转账")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "fromAddress", parameterDes = "交易创建者账户地址"),
        @Parameter(parameterName = "password", parameterDes = "调用者账户密码"),
        @Parameter(parameterName = "toAddress", parameterDes = "调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO"),
        @Parameter(parameterName = "contractAddress", parameterDes = "合约地址"),
        @Parameter(parameterName = "amount", requestType = @TypeDescriptor(value = BigInteger.class), parameterDes = "合约方法"),
        @Parameter(parameterName = "remark",  parameterDes = "交易备注", canNull = true)
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "txHash", description = "交易hash")
    }))
    public RpcResult tokentransfer(List<Object> params) {
        try {
            int i = 0;
            Integer chainId = (Integer) params.get(i++);
            String fromAddress = (String) params.get(i++);
            String password = (String) params.get(i++);
            String toAddress = (String) params.get(i++);
            String contractAddress = (String) params.get(i++);
            Object amountObj = params.get(i++);
            if(amountObj == null) {
                return RpcResult.paramError("amount is empty");
            }
            BigInteger amount = new BigInteger(amountObj.toString());
            if (amount.compareTo(BigInteger.ZERO) < 0) {
                return RpcResult.paramError(String.format("amount [%s] is invalid", amount.toString()));
            }
            String remark = (String) params.get(i++);


            if (!AddressTool.validAddress(chainId, fromAddress)) {
                return RpcResult.paramError(String.format("fromAddress [%s] is invalid", fromAddress));
            }
            if (!AddressTool.validAddress(chainId, toAddress)) {
                return RpcResult.paramError(String.format("toAddress [%s] is invalid", toAddress));
            }
            if (!AddressTool.validAddress(chainId, contractAddress)) {
                return RpcResult.paramError(String.format("contractAddress [%s] is invalid", contractAddress));
            }

            TokenTransferReq req = new TokenTransferReq();
            req.setChainId(config.getChainId());
            req.setAddress(fromAddress);
            req.setPassword(password);
            req.setToAddress(toAddress);
            req.setContractAddress(contractAddress);
            req.setAmount(amount.toString());
            req.setRemark(remark);
            Result<String> result = contractProvider.tokenTransfer(req);
            RpcResult rpcResult = ResultUtil.getJsonRpcResult(result);
            if(rpcResult.getError() == null) {
                Map dataMap = new HashMap();
                dataMap.put("txHash", rpcResult.getResult());
                rpcResult.setResult(dataMap);
            }
            return rpcResult;
        } catch (Exception e) {
            Log.error(e);
            return RpcResult.failed(CommonCodeConstanst.DATA_ERROR, e.getMessage());
        }
    }


    @RpcMethod("transfer2contract")
    @ApiOperation(description = "从账户地址向合约地址转账(主链资产)的合约交易")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "fromAddress", parameterDes = "交易创建者账户地址"),
        @Parameter(parameterName = "password", parameterDes = "调用者账户密码"),
        @Parameter(parameterName = "toAddress", parameterDes = "调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO"),
        @Parameter(parameterName = "amount", requestType = @TypeDescriptor(value = BigInteger.class), parameterDes = "合约方法"),
        @Parameter(parameterName = "remark",  parameterDes = "交易备注", canNull = true)
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "txHash", description = "交易hash")
    }))
    public RpcResult transfer2contract(List<Object> params) {
        VerifyUtils.verifyParams(params, 6);
        try {
            int i = 0;
            Integer chainId = (Integer) params.get(i++);
            if (!Context.isChainExist(chainId)) {
                return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
            }
            String fromAddress = (String) params.get(i++);
            String password = (String) params.get(i++);
            String toAddress = (String) params.get(i++);
            Object amountObj = params.get(i++);
            if(amountObj == null) {
                return RpcResult.paramError("amount is empty");
            }
            BigInteger amount = new BigInteger(amountObj.toString());
            if (amount.compareTo(BigInteger.ZERO) < 0) {
                return RpcResult.paramError(String.format("amount [%s] is invalid", amount.toString()));
            }
            String remark = (String) params.get(i++);

            if (!AddressTool.validAddress(chainId, fromAddress)) {
                return RpcResult.paramError(String.format("fromAddress [%s] is invalid", fromAddress));
            }
            if (!AddressTool.validAddress(chainId, toAddress)) {
                return RpcResult.paramError(String.format("toAddress [%s] is invalid", toAddress));
            }

            TransferToContractReq req = new TransferToContractReq(
                    fromAddress,
                    toAddress,
                    amount,
                    password,
                    remark);
            req.setChainId(config.getChainId());
            Result<String> result = contractProvider.transferToContract(req);
            RpcResult rpcResult = ResultUtil.getJsonRpcResult(result);
            if(rpcResult.getError() == null) {
                Map dataMap = new HashMap();
                dataMap.put("txHash", rpcResult.getResult());
                rpcResult.setResult(dataMap);
            }
            return rpcResult;
        } catch (Exception e) {
            Log.error(e);
            return RpcResult.failed(CommonCodeConstanst.DATA_ERROR, e.getMessage());
        }
    }


    @RpcMethod("validateContractCreate")
    @ApiOperation(description = "验证发布合约")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "sender", parameterDes = "交易创建者账户地址"),
        @Parameter(parameterName = "gasLimit", requestType = @TypeDescriptor(value = long.class), parameterDes = "GAS限制"),
        @Parameter(parameterName = "price", requestType = @TypeDescriptor(value = long.class), parameterDes = "GAS单价"),
        @Parameter(parameterName = "contractCode", parameterDes = "智能合约代码(字节码的Hex编码字符串)"),
        @Parameter(parameterName = "args", requestType = @TypeDescriptor(value = Object[].class), parameterDes = "参数列表", canNull = true)
    })
    @ResponseData(name = "返回值", description = "返回消耗的gas值", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "success", valueType = boolean.class, description = "验证成功与否"),
        @Key(name = "code", description = "验证失败的错误码"),
        @Key(name = "msg", description = "验证失败的错误信息")
    }))
    public RpcResult validateContractCreate(List<Object> params) {
        VerifyUtils.verifyParams(params, 6);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
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


    @RpcMethod("validateContractCall")
    @ApiOperation(description = "验证调用合约")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "sender", parameterDes = "交易创建者账户地址"),
        @Parameter(parameterName = "value", requestType = @TypeDescriptor(value = BigInteger.class), parameterDes = "调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO"),
        @Parameter(parameterName = "gasLimit", requestType = @TypeDescriptor(value = long.class), parameterDes = "GAS限制"),
        @Parameter(parameterName = "price", requestType = @TypeDescriptor(value = long.class), parameterDes = "GAS单价"),
        @Parameter(parameterName = "contractAddress", parameterDes = "合约地址"),
        @Parameter(parameterName = "methodName", parameterDes = "合约方法"),
        @Parameter(parameterName = "methodDesc", parameterDes = "合约方法描述，若合约内方法没有重载，则此参数可以为空", canNull = true),
        @Parameter(parameterName = "args", requestType = @TypeDescriptor(value = Object[].class), parameterDes = "参数列表", canNull = true)
    })
    @ResponseData(name = "返回值", description = "返回消耗的gas值", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "success", valueType = boolean.class, description = "验证成功与否"),
        @Key(name = "code", description = "验证失败的错误码"),
        @Key(name = "msg", description = "验证失败的错误信息")
    }))
    public RpcResult validateContractCall(List<Object> params) {
        VerifyUtils.verifyParams(params, 9);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
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

    @RpcMethod("validateContractDelete")
    @ApiOperation(description = "验证删除合约")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "sender", parameterDes = "交易创建者账户地址"),
        @Parameter(parameterName = "contractAddress", parameterDes = "合约地址")
    })
    @ResponseData(name = "返回值", description = "返回消耗的gas值", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "success", valueType = boolean.class, description = "验证成功与否"),
        @Key(name = "code", description = "验证失败的错误码"),
        @Key(name = "msg", description = "验证失败的错误信息")
    }))
    public RpcResult validateContractDelete(List<Object> params) {
        VerifyUtils.verifyParams(params, 3);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
        }
        RpcResult rpcResult = new RpcResult();
        Result<Map> mapResult = contractTools.validateContractDelete(chainId,
                params.get(1),
                params.get(2)
        );
        rpcResult.setResult(mapResult.getData());
        return rpcResult;
    }

    @RpcMethod("imputedContractCreateGas")
    @ApiOperation(description = "估算发布合约交易的GAS")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "sender", parameterDes = "交易创建者账户地址"),
        @Parameter(parameterName = "contractCode", parameterDes = "智能合约代码(字节码的Hex编码字符串)"),
        @Parameter(parameterName = "args", requestType = @TypeDescriptor(value = Object[].class), parameterDes = "参数列表", canNull = true)
    })
    @ResponseData(name = "返回值", description = "返回消耗的gas值", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "gasLimit", valueType = Long.class, description = "消耗的gas值，执行失败返回数值1")
    }))
    public RpcResult imputedContractCreateGas(List<Object> params) {
        VerifyUtils.verifyParams(params, 4);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
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

    @RpcMethod("imputedContractCallGas")
    @ApiOperation(description = "估算调用合约交易的GAS")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "sender", parameterDes = "交易创建者账户地址"),
        @Parameter(parameterName = "value", requestType = @TypeDescriptor(value = BigInteger.class), parameterDes = "调用者向合约地址转入的主网资产金额，没有此业务时填BigInteger.ZERO"),
        @Parameter(parameterName = "contractAddress", parameterDes = "合约地址"),
        @Parameter(parameterName = "methodName", parameterDes = "合约方法"),
        @Parameter(parameterName = "methodDesc", parameterDes = "合约方法描述，若合约内方法没有重载，则此参数可以为空", canNull = true),
        @Parameter(parameterName = "args", requestType = @TypeDescriptor(value = Object[].class), parameterDes = "参数列表", canNull = true)
    })
    @ResponseData(name = "返回值", description = "返回消耗的gas值", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "gasLimit", valueType = Long.class, description = "消耗的gas值，执行失败返回数值1")
    }))
    public RpcResult imputedContractCallGas(List<Object> params) {
        VerifyUtils.verifyParams(params, 7);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
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

    @RpcMethod("invokeView")
    @ApiOperation(description = "调用合约不上链方法")
    @Parameters(value = {
        @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链id"),
        @Parameter(parameterName = "contractAddress", parameterDes = "合约地址"),
        @Parameter(parameterName = "methodName", parameterDes = "合约方法"),
        @Parameter(parameterName = "methodDesc", parameterDes = "合约方法描述，若合约内方法没有重载，则此参数可以为空", canNull = true),
        @Parameter(parameterName = "args", requestType = @TypeDescriptor(value = Object[].class), parameterDes = "参数列表", canNull = true)
    })
    @ResponseData(name = "返回值", description = "返回Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "result", description = "视图方法的调用结果")
    }))
    public RpcResult invokeView(List<Object> params) {
        VerifyUtils.verifyParams(params, 5);
        int chainId;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is invalid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.paramError(String.format("chainId [%s] is invalid", chainId));
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
