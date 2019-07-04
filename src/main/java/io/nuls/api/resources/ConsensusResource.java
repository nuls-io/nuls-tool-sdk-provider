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
import io.nuls.base.api.provider.consensus.ConsensusProvider;
import io.nuls.base.api.provider.consensus.facade.CreateAgentReq;
import io.nuls.base.api.provider.consensus.facade.DepositToAgentReq;
import io.nuls.base.api.provider.consensus.facade.StopAgentReq;
import io.nuls.base.api.provider.consensus.facade.WithdrawReq;
import io.nuls.base.api.provider.transaction.TransferService;
import io.nuls.base.api.provider.transaction.facade.TransferReq;
import io.nuls.core.constant.CommonCodeConstanst;
import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Component;
import io.nuls.core.rpc.model.*;
import io.nuls.model.ErrorData;
import io.nuls.model.RpcClientResult;
import io.nuls.v2.model.annotation.Api;
import io.nuls.v2.model.annotation.ApiOperation;
import io.nuls.model.form.TransferForm;
import io.nuls.model.form.consensus.CreateAgentForm;
import io.nuls.model.form.consensus.DepositForm;
import io.nuls.model.form.consensus.StopAgentForm;
import io.nuls.model.form.consensus.WithdrawForm;
import io.nuls.utils.ResultUtil;
import io.nuls.v2.model.dto.ConsensusDto;
import io.nuls.v2.model.dto.DepositDto;
import io.nuls.v2.model.dto.StopConsensusDto;
import io.nuls.v2.model.dto.WithDrawDto;
import io.nuls.v2.util.NulsSDKTool;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author: PierreLuo
 * @date: 2019-06-27
 */
@Path("/api/consensus")
@Component
@Api
public class ConsensusResource {

    @Autowired
    private Config config;

    ConsensusProvider consensusProvider = ServiceManager.get(ConsensusProvider.class);

    @POST
    @Path("/agent")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "Create an agent for consensus! 创建共识(代理)节点")
    @Parameters({
            @Parameter(parameterName = "创建共识(代理)节点", parameterDes = "创建共识(代理)节点表单", requestType = @TypeDescriptor(value = CreateAgentForm.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "交易hash")
    }))
    public RpcClientResult createAgent(CreateAgentForm form) {
        if (form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR.getCode(), "form is empty"));
        }
        CreateAgentReq req = new CreateAgentReq(
                form.getAgentAddress(),
                form.getPackingAddress(),
                form.getRewardAddress(),
                form.getCommissionRate(),
                new BigInteger(form.getDeposit()),
                form.getPassword());
        req.setChainId(config.getChainId());
        Result<String> result = consensusProvider.createAgent(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("value", clientResult.getData()).mapToData();
        }
        return clientResult;
    }

    @POST
    @Path("/agent/stop")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "注销共识节点")
    @Parameters({
            @Parameter(parameterName = "注销共识节点", parameterDes = "注销共识节点表单", requestType = @TypeDescriptor(value = StopAgentForm.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "交易hash")
    }))
    public RpcClientResult stopAgent(StopAgentForm form) {
        if (form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR.getCode(), "form is empty"));
        }
        StopAgentReq req = new StopAgentReq(
                form.getAddress(),
                form.getPassword());
        req.setChainId(config.getChainId());
        Result<String> result = consensusProvider.stopAgent(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("value", clientResult.getData()).mapToData();
        }
        return clientResult;
    }

    @POST
    @Path("/deposit")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "deposit nuls to a bank! 申请参与共识")
    @Parameters({
            @Parameter(parameterName = "申请参与共识", parameterDes = "申请参与共识表单", requestType = @TypeDescriptor(value = DepositForm.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "交易hash")
    }))
    public RpcClientResult depositToAgent(DepositForm form) {
        if (form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR.getCode(), "form is empty"));
        }
        DepositToAgentReq req = new DepositToAgentReq(
                form.getAddress(),
                form.getAgentHash(),
                new BigInteger(form.getDeposit()),
                form.getPassword());
        req.setChainId(config.getChainId());
        Result<String> result = consensusProvider.depositToAgent(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("value", clientResult.getData()).mapToData();
        }
        return clientResult;
    }


    @POST
    @Path("/withdraw")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "退出共识")
    @Parameters({
            @Parameter(parameterName = "退出共识", parameterDes = "退出共识表单", requestType = @TypeDescriptor(value = WithdrawForm.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "交易hash")
    }))
    public RpcClientResult withdraw(WithdrawForm form) {
        if (form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR.getCode(), "form is empty"));
        }
        WithdrawReq req = new WithdrawReq(
                form.getAddress(),
                form.getTxHash(),
                form.getPassword());
        req.setChainId(config.getChainId());
        Result<String> result = consensusProvider.withdraw(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("value", clientResult.getData()).mapToData();
        }
        return clientResult;
    }


    @POST
    @Path("/agent/offline")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "离线组装 - 创建共识(代理)节点")
    @Parameters({
            @Parameter(parameterName = "离线创建共识(代理)节点", parameterDes = "离线创建共识(代理)节点表单", requestType = @TypeDescriptor(value = ConsensusDto.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "hash", description = "交易hash"),
            @Key(name = "txHex", description = "交易序列化字符串")
    }))
    public RpcClientResult createAgentOffline(ConsensusDto form) {
        if (form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR.getCode(), "form is empty"));
        }
        io.nuls.core.basic.Result result = NulsSDKTool.createConsensusTx(form);
        return ResultUtil.getRpcClientResult(result);
    }


    @POST
    @Path("/agent/stop/offline")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "离线组装 - 注销共识节点")
    @Parameters({
            @Parameter(parameterName = "离线注销共识节点", parameterDes = "离线注销共识节点表单", requestType = @TypeDescriptor(value = StopConsensusDto.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "hash", description = "交易hash"),
            @Key(name = "txHex", description = "交易序列化字符串")
    }))
    public RpcClientResult stopAgentOffline(StopConsensusDto form) {
        if (form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR.getCode(), "form is empty"));
        }
        io.nuls.core.basic.Result result = NulsSDKTool.createStopConsensusTx(form);
        return ResultUtil.getRpcClientResult(result);
    }


    @POST
    @Path("/deposit/offline")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "离线组装 - 申请参与共识")
    @Parameters({
            @Parameter(parameterName = "离线申请参与共识", parameterDes = "离线申请参与共识表单", requestType = @TypeDescriptor(value = DepositDto.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "hash", description = "交易hash"),
            @Key(name = "txHex", description = "交易序列化字符串")
    }))
    public RpcClientResult depositToAgentOffline(DepositDto form) {
        if (form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR.getCode(), "form is empty"));
        }
        io.nuls.core.basic.Result result = NulsSDKTool.createDepositTx(form);
        return ResultUtil.getRpcClientResult(result);
    }


    @POST
    @Path("/withdraw/offline")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "离线组装 - 退出共识")
    @Parameters({
            @Parameter(parameterName = "离线退出共识", parameterDes = "离线退出共识表单", requestType = @TypeDescriptor(value = WithDrawDto.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "hash", description = "交易hash"),
            @Key(name = "txHex", description = "交易序列化字符串")
    }))
    public RpcClientResult withdrawOffline(WithDrawDto form) {
        if (form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR.getCode(), "form is empty"));
        }
        io.nuls.core.basic.Result result = NulsSDKTool.createWithdrawDepositTx(form);
        return ResultUtil.getRpcClientResult(result);
    }

}
