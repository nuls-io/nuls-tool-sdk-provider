/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.nuls.api.resources;

import io.nuls.api.config.Config;
import io.nuls.base.api.provider.Result;
import io.nuls.base.api.provider.ServiceManager;
import io.nuls.base.api.provider.contract.ContractProvider;
import io.nuls.base.api.provider.contract.facade.*;
import io.nuls.core.constant.CommonCodeConstanst;
import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Component;
import io.nuls.core.rpc.model.*;
import io.nuls.model.ErrorData;
import io.nuls.model.RpcClientResult;
import io.nuls.model.form.contract.*;
import io.nuls.v2.model.annotation.Api;
import io.nuls.v2.model.annotation.ApiOperation;
import io.nuls.model.dto.ContractInfoDto;
import io.nuls.model.dto.ContractResultDto;
import io.nuls.model.dto.ContractTokenInfoDto;
import io.nuls.rpctools.ContractTools;
import io.nuls.utils.ResultUtil;
import io.nuls.v2.model.dto.ContractConstructorInfoDto;
import io.nuls.v2.service.ContractService;
import io.nuls.v2.util.NulsSDKTool;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author: PierreLuo
 * @date: 2019-06-27
 */
@Path("/api/contract")
@Component
@Api
public class ContractResource {

    ContractProvider contractProvider = ServiceManager.get(ContractProvider.class);
    @Autowired
    ContractTools contractTools;
    @Autowired
    Config config;

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "发布合约")
    @Parameters({
            @Parameter(parameterName = "发布合约", parameterDes = "发布合约表单", requestType = @TypeDescriptor(value = ContractCreate.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map对象，包含两个属性", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "txHash", description = "发布合约的交易hash"),
            @Key(name = "contractAddress", description = "生成的合约地址")
    }))
    public RpcClientResult createContract(ContractCreate create) {
        if (create == null || create.getGasLimit() < 0 || create.getPrice() < 0) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        CreateContractReq req = new CreateContractReq();
        req.setChainId(config.getChainId());
        req.setSender(create.getSender());
        req.setPassword(create.getPassword());
        req.setPrice(create.getPrice());
        req.setGasLimit(create.getGasLimit());
        req.setContractCode(create.getContractCode());
        req.setAlias(create.getAlias());
        req.setArgs(create.getArgs());
        req.setRemark(create.getRemark());
        Result<Map> result = contractProvider.createContract(req);
        return ResultUtil.getRpcClientResult(result);
    }

    @POST
    @Path("/call")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "调用合约")
    @Parameters({
            @Parameter(parameterName = "调用合约", parameterDes = "调用合约表单", requestType = @TypeDescriptor(value = ContractCall.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map对象", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "txHash", description = "调用合约的交易hash")
    }))
    public RpcClientResult callContract(ContractCall call) {
        if (call == null || call.getValue() == null || call.getValue().compareTo(BigInteger.ZERO) < 0 || call.getGasLimit() < 0 || call.getPrice() < 0) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        CallContractReq req = new CallContractReq();
        req.setChainId(config.getChainId());
        req.setSender(call.getSender());
        req.setPassword(call.getPassword());
        req.setPrice(call.getPrice());
        req.setGasLimit(call.getGasLimit());
        req.setValue(call.getValue().longValue());
        req.setMethodName(call.getMethodName());
        req.setMethodDesc(call.getMethodDesc());
        req.setContractAddress(call.getContractAddress());
        req.setArgs(call.getArgs());
        req.setRemark(call.getRemark());
        Result<String> result = contractProvider.callContract(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            String hash = (String) clientResult.getData();
            return clientResult.resultMap().map("txHash", hash).mapToData();
        }
        return clientResult;
    }

    @POST
    @Path("/tokentransfer")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "token转账")
    @Parameters({
            @Parameter(parameterName = "token转账", parameterDes = "token转账表单", requestType = @TypeDescriptor(value = ContractTokenTransfer.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map对象", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "txHash", description = "交易hash")
    }))
    public RpcClientResult tokentransfer(ContractTokenTransfer form) {
        if (form == null || form.getAmount() == null || form.getAmount().compareTo(BigInteger.ZERO) < 0) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        TokenTransferReq req = new TokenTransferReq();
        req.setChainId(config.getChainId());
        req.setAddress(form.getFromAddress());
        req.setPassword(form.getPassword());
        req.setToAddress(form.getToAddress());
        req.setContractAddress(form.getContractAddress());
        req.setAmount(form.getAmount().toString());
        req.setRemark(form.getRemark());
        Result<String> result = contractProvider.tokenTransfer(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            String hash = (String) clientResult.getData();
            return clientResult.resultMap().map("txHash", hash).mapToData();
        }
        return clientResult;
    }


    @POST
    @Path("/transfer2contract")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "从账户地址向合约地址转账(主链资产)的合约交易")
    @Parameters({
        @Parameter(parameterName = "向合约地址转账", parameterDes = "向合约地址转账表单", requestType = @TypeDescriptor(value = ContractTransfer.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map对象", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "txHash", description = "交易hash")
    }))
    public RpcClientResult tokentransfer(ContractTransfer form) {
        if (form == null || form.getAmount() == null || form.getAmount().compareTo(BigInteger.ZERO) < 0) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        TransferToContractReq req = new TransferToContractReq(
                form.getFromAddress(),
                form.getToAddress(),
                form.getAmount(),
                form.getPassword(),
                form.getRemark());
        req.setChainId(config.getChainId());
        Result<String> result = contractProvider.transferToContract(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            String hash = (String) clientResult.getData();
            return clientResult.resultMap().map("txHash", hash).mapToData();
        }
        return clientResult;
    }

    @POST
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "删除合约")
    @Parameters({
            @Parameter(parameterName = "删除合约", parameterDes = "删除合约表单", requestType = @TypeDescriptor(value = ContractDelete.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map对象", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "txHash", description = "删除合约的交易hash")
    }))
    public RpcClientResult deleteContract(ContractDelete delete) {
        if (delete == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        DeleteContractReq req = new DeleteContractReq(delete.getSender(), delete.getContractAddress(), delete.getPassword());
        req.setChainId(config.getChainId());
        req.setRemark(delete.getRemark());
        Result<String> result = contractProvider.deleteContract(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            String hash = (String) clientResult.getData();
            return clientResult.resultMap().map("txHash", hash).mapToData();
        }
        return clientResult;
    }

    @GET
    @Path("/balance/token/{contractAddress}/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "获取账户地址的指定token余额")
    @Parameters({
            @Parameter(parameterName = "contractAddress", parameterDes = "合约地址"),
            @Parameter(parameterName = "address", parameterDes = "账户地址")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = ContractTokenInfoDto.class))
    public RpcClientResult getBalance(@PathParam("contractAddress") String contractAddress, @PathParam("address") String address) {
        if (address == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        Result<ContractTokenInfoDto> result = contractTools.getTokenBalance(config.getChainId(), contractAddress, address);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        return clientResult;
    }

    @GET
    @Path("/info/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "获取智能合约详细信息")
    @Parameters({
            @Parameter(parameterName = "address", parameterDes = "合约地址")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = ContractInfoDto.class))
    public RpcClientResult getContractDetailInfo(@PathParam("address") String address) {
        if (address == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        Result<Map> result = contractTools.getContractInfo(config.getChainId(), address);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        return clientResult;
    }

    @GET
    @Path("/result/{hash}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "获取智能合约执行结果")
    @Parameters({
            @Parameter(parameterName = "hash", parameterDes = "交易hash")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = ContractResultDto.class))
    public RpcClientResult getContractResult(@PathParam("hash") String hash) {
        if (hash == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        Result<Map> result = contractTools.getContractResult(config.getChainId(), hash);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        return clientResult;
    }


    @POST
    @Path("/create/offline")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "离线组装 - 发布合约的交易")
    @Parameters(value = {
        @Parameter(parameterName = "发布合约离线交易", parameterDes = "发布合约离线交易表单", requestType = @TypeDescriptor(value = ContractCreateOffline.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map对象", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "hash", description = "交易hash"),
        @Key(name = "txHex", description = "交易序列化字符串"),
        @Key(name = "contractAddress", description = "生成的合约地址")
    }))
    public RpcClientResult createTxOffline(ContractCreateOffline form) {
        io.nuls.core.basic.Result<Map> result = NulsSDKTool.createContractTx(
                form.getSender(),
                form.getAlias(),
                form.getContractCode(),
                form.getArgs(),
                form.getRemark());
        return ResultUtil.getRpcClientResult(result);
    }

    @POST
    @Path("/call/offline")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "离线组装 - 调用合约的交易")
    @Parameters(value = {
        @Parameter(parameterName = "调用合约离线交易", parameterDes = "调用合约离线交易表单", requestType = @TypeDescriptor(value = ContractCallOffline.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "hash", description = "交易hash"),
        @Key(name = "txHex", description = "交易序列化字符串")
    }))
    public RpcClientResult callTxOffline(ContractCallOffline form) {
        io.nuls.core.basic.Result<Map> result = NulsSDKTool.callContractTxOffline(
                form.getSender(),
                form.getValue(),
                form.getContractAddress(),
                form.getMethodName(),
                form.getMethodDesc(),
                form.getArgs(),
                form.getRemark());
        return ResultUtil.getRpcClientResult(result);
    }


    @POST
    @Path("/delete/offline")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "离线组装 - 删除合约交易")
    @Parameters(value = {
        @Parameter(parameterName = "删除合约离线交易", parameterDes = "删除合约离线交易表单", requestType = @TypeDescriptor(value = ContractDeleteOffline.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "hash", description = "交易hash"),
        @Key(name = "txHex", description = "交易序列化字符串")
    }))
    public RpcClientResult deleteTxOffline(ContractDeleteOffline form) {
        io.nuls.core.basic.Result<Map> result = NulsSDKTool.deleteContractTxOffline(
                form.getSender(),
                form.getContractAddress(),
                form.getRemark());
        return ResultUtil.getRpcClientResult(result);
    }

    @POST
    @Path("/tokentransfer/offline")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "离线组装 - token转账交易")
    @Parameters(value = {
        @Parameter(parameterName = "token转账离线交易", parameterDes = "token转账离线交易表单", requestType = @TypeDescriptor(value = ContractTokenTransferOffline.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "hash", description = "交易hash"),
        @Key(name = "txHex", description = "交易序列化字符串")
    }))
    public RpcClientResult tokenTransferOffline(ContractTokenTransferOffline form) {
        io.nuls.core.basic.Result<Map> result = NulsSDKTool.tokenTransfer(
                form.getFromAddress(),
                form.getToAddress(),
                form.getContractAddress(),
                form.getAmount(),
                form.getRemark());
        return ResultUtil.getRpcClientResult(result);
    }

    @POST
    @Path("/transfer2contract/offline")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "离线组装 - 从账户地址向合约地址转账(主链资产)的合约交易")
    @Parameters(value = {
        @Parameter(parameterName = "向合约地址转账离线交易", parameterDes = "向合约地址转账离线交易表单", requestType = @TypeDescriptor(value = ContractTransferOffline.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
        @Key(name = "hash", description = "交易hash"),
        @Key(name = "txHex", description = "交易序列化字符串")
    }))
    public RpcClientResult tokenToContractOffline(ContractTransferOffline form) {
        io.nuls.core.basic.Result<Map> result = NulsSDKTool.tokenToContract(
                form.getFromAddress(),
                form.getToAddress(),
                form.getAmount(),
                form.getRemark());
        return ResultUtil.getRpcClientResult(result);
    }
}
