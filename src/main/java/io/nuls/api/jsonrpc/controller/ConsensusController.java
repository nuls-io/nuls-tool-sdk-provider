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

import io.nuls.base.api.provider.Result;
import io.nuls.base.api.provider.ServiceManager;
import io.nuls.base.api.provider.consensus.ConsensusProvider;
import io.nuls.base.api.provider.consensus.facade.CreateAgentReq;
import io.nuls.base.api.provider.consensus.facade.DepositToAgentReq;
import io.nuls.base.api.provider.consensus.facade.StopAgentReq;
import io.nuls.base.api.provider.consensus.facade.WithdrawReq;
import io.nuls.base.basic.AddressTool;
import io.nuls.core.core.annotation.Controller;
import io.nuls.core.core.annotation.RpcMethod;
import io.nuls.core.model.FormatValidUtils;
import io.nuls.core.model.StringUtils;
import io.nuls.core.parse.JSONUtils;
import io.nuls.core.rpc.model.*;
import io.nuls.model.form.consensus.CreateAgentForm;
import io.nuls.model.form.consensus.DepositForm;
import io.nuls.model.form.consensus.StopAgentForm;
import io.nuls.model.form.consensus.WithdrawForm;
import io.nuls.model.jsonrpc.RpcResult;
import io.nuls.utils.ResultUtil;
import io.nuls.v2.model.annotation.ApiOperation;
import io.nuls.v2.model.dto.CoinFromDto;
import io.nuls.v2.model.dto.ConsensusDto;
import io.nuls.v2.model.dto.StopConsensusDto;
import io.nuls.v2.util.NulsSDKTool;
import io.nuls.v2.util.ValidateUtil;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author Niels
 */
@Controller
public class ConsensusController {

    ConsensusProvider consensusProvider = ServiceManager.get(ConsensusProvider.class);

    @RpcMethod("createAgent")
    @ApiOperation(description = "Create an agent for consensus! 创建共识(代理)节点")
    @Parameters({
            @Parameter(parameterName = "创建共识(代理)节点", parameterDes = "创建共识(代理)节点表单", requestType = @TypeDescriptor(value = CreateAgentForm.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "交易hash")
    }))
    public RpcResult createAgent(List<Object> params) {
        String agentAddress, packingAddress, rewardAddress, deposit, password;
        int chainId, commissionRate;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            agentAddress = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[agentAddress] is inValid");
        }
        try {
            packingAddress = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[packingAddress] is inValid");
        }
        try {
            rewardAddress = (String) params.get(3);
        } catch (Exception e) {
            return RpcResult.paramError("[rewardAddress] is inValid");
        }
        try {
            deposit = (String) params.get(4);
        } catch (Exception e) {
            return RpcResult.paramError("[deposit] is inValid");
        }
        try {
            commissionRate = (int) params.get(5);
        } catch (Exception e) {
            return RpcResult.paramError("[commissionRate] is inValid");
        }
        try {
            password = (String) params.get(6);
        } catch (Exception e) {
            return RpcResult.paramError("[password] is inValid");
        }
        if (!AddressTool.validAddress(chainId, agentAddress)) {
            return RpcResult.paramError("[agentAddress] is inValid");
        }
        if (!AddressTool.validAddress(chainId, packingAddress)) {
            return RpcResult.paramError("[packingAddress] is inValid");
        }
        if (!AddressTool.validAddress(chainId, rewardAddress)) {
            return RpcResult.paramError("[rewardAddress] is inValid");
        }
        if (!ValidateUtil.validateBigInteger(deposit)) {
            return RpcResult.paramError("[deposit] is inValid");
        }

        CreateAgentReq req = new CreateAgentReq(agentAddress, packingAddress, rewardAddress, commissionRate, new BigInteger(deposit), password);
        req.setChainId(chainId);
        Result<String> result = consensusProvider.createAgent(req);
        RpcResult rpcResult = ResultUtil.getJsonRpcResult(result);
        return rpcResult;
    }

    @RpcMethod("stopAgent")
    @ApiOperation(description = "注销共识节点")
    @Parameters({
            @Parameter(parameterName = "注销共识节点", parameterDes = "注销共识节点表单", requestType = @TypeDescriptor(value = StopAgentForm.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "交易hash")
    }))
    public RpcResult stopAgent(List<Object> params) {
        int chainId;
        String address, password;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            address = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[address] is inValid");
        }
        try {
            password = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[password] is inValid");
        }
        if (!AddressTool.validAddress(chainId, address)) {
            return RpcResult.paramError("[address] is inValid");
        }
        if (!FormatValidUtils.validPassword(password)) {
            return RpcResult.paramError("[password] is inValid");
        }

        StopAgentReq req = new StopAgentReq(address, password);
        req.setChainId(chainId);
        Result<String> result = consensusProvider.stopAgent(req);
        RpcResult rpcResult = ResultUtil.getJsonRpcResult(result);
        return rpcResult;
    }

    @RpcMethod("depositToAgent")
    @ApiOperation(description = "deposit nuls to a bank! 申请参与共识")
    @Parameters({
            @Parameter(parameterName = "申请参与共识", parameterDes = "申请参与共识表单", requestType = @TypeDescriptor(value = DepositForm.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "交易hash")
    }))
    public RpcResult depositToAgent(List<Object> params) {
        int chainId;
        String address, agentHash, deposit, password;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            address = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[address] is inValid");
        }
        try {
            agentHash = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[agentHash] is inValid");
        }
        try {
            deposit = (String) params.get(3);
        } catch (Exception e) {
            return RpcResult.paramError("[deposit] is inValid");
        }
        try {
            password = (String) params.get(4);
        } catch (Exception e) {
            return RpcResult.paramError("[password] is inValid");
        }
        if (!AddressTool.validAddress(chainId, address)) {
            return RpcResult.paramError("[address] is inValid");
        }
        if (!FormatValidUtils.validPassword(password)) {
            return RpcResult.paramError("[password] is inValid");
        }
        if (!ValidateUtil.validateBigInteger(deposit)) {
            return RpcResult.paramError("[deposit] is inValid");
        }
        if (StringUtils.isBlank(agentHash)) {
            return RpcResult.paramError("[agentHash] is inValid");
        }
        DepositToAgentReq req = new DepositToAgentReq(address, agentHash, new BigInteger(deposit), password);
        req.setChainId(chainId);
        Result<String> result = consensusProvider.depositToAgent(req);
        RpcResult rpcResult = ResultUtil.getJsonRpcResult(result);
        return rpcResult;
    }

    @RpcMethod("withdraw")
    @ApiOperation(description = "退出共识")
    @Parameters({
            @Parameter(parameterName = "退出共识", parameterDes = "退出共识表单", requestType = @TypeDescriptor(value = WithdrawForm.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "交易hash")
    }))
    public RpcResult withdraw(List<Object> params) {
        int chainId;
        String address, txHash, password;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            address = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[address] is inValid");
        }
        try {
            txHash = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[txHash] is inValid");
        }
        try {
            password = (String) params.get(3);
        } catch (Exception e) {
            return RpcResult.paramError("[password] is inValid");
        }
        WithdrawReq req = new WithdrawReq(address, txHash, password);
        req.setChainId(chainId);
        Result<String> result = consensusProvider.withdraw(req);
        RpcResult rpcResult = ResultUtil.getJsonRpcResult(result);
        return rpcResult;
    }

    @RpcMethod("createAgentOffline")
    @ApiOperation(description = "离线组装 - 创建共识(代理)节点")
    @Parameters({
            @Parameter(parameterName = "离线创建共识(代理)节点", parameterDes = "离线创建共识(代理)节点表单", requestType = @TypeDescriptor(value = ConsensusDto.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "hash", description = "交易hash"),
            @Key(name = "txHex", description = "交易序列化字符串")
    }))
    public RpcResult createAgentOffline(List<Object> params) {
        String agentAddress, packingAddress, rewardAddress, deposit;
        int chainId, commissionRate;
        Map map;
        CoinFromDto fromDto;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            agentAddress = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[agentAddress] is inValid");
        }
        try {
            packingAddress = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[packingAddress] is inValid");
        }
        try {
            rewardAddress = (String) params.get(3);
        } catch (Exception e) {
            return RpcResult.paramError("[rewardAddress] is inValid");
        }
        try {
            deposit = (String) params.get(4);
        } catch (Exception e) {
            return RpcResult.paramError("[deposit] is inValid");
        }
        try {
            commissionRate = (int) params.get(5);
        } catch (Exception e) {
            return RpcResult.paramError("[commissionRate] is inValid");
        }
        try {
            map = (Map) params.get(5);
            String amount = (String) map.get("amount");
            map.put("amount", new BigInteger(amount));
            fromDto = JSONUtils.map2pojo(map, CoinFromDto.class);
        } catch (Exception e) {
            return RpcResult.paramError("[input] is inValid");
        }

        ConsensusDto form = new ConsensusDto();
        form.setAgentAddress(agentAddress);
        form.setPackingAddress(packingAddress);
        form.setRewardAddress(rewardAddress);
        form.setDeposit(new BigInteger(deposit));
        form.setCommissionRate(commissionRate);
        form.setInput(fromDto);

        if (!AddressTool.validAddress(chainId, agentAddress)) {
            return RpcResult.paramError("[agentAddress] is inValid");
        }
        if (!AddressTool.validAddress(chainId, packingAddress)) {
            return RpcResult.paramError("[packingAddress] is inValid");
        }
        if (!AddressTool.validAddress(chainId, rewardAddress)) {
            return RpcResult.paramError("[rewardAddress] is inValid");
        }
        if (!ValidateUtil.validateBigInteger(deposit)) {
            return RpcResult.paramError("[deposit] is inValid");
        }

        io.nuls.core.basic.Result result = NulsSDKTool.createConsensusTxOffline(form);
        RpcResult rpcResult = ResultUtil.getJsonRpcResult(result);
        return rpcResult;
    }


    @RpcMethod("stopAgentOffline")
    @ApiOperation(description = "离线组装 - 注销共识节点")
    @Parameters({
            @Parameter(parameterName = "离线注销共识节点", parameterDes = "离线注销共识节点表单", requestType = @TypeDescriptor(value = StopConsensusDto.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "hash", description = "交易hash"),
            @Key(name = "txHex", description = "交易序列化字符串")
    }))
    public RpcResult stopAgentOffline(List<Object> params) {
        int chainId;
        String agentHash,agentAddress,deposit,price;
        List<Map> mapList;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            agentHash = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[agentHash] is inValid");
        }
        try {
            agentAddress = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[agentAddress] is inValid");
        }
        try {
            deposit = (String) params.get(3);
        } catch (Exception e) {
            return RpcResult.paramError("[deposit] is inValid");
        }
        try {
            price = (String) params.get(4);
        } catch (Exception e) {
            return RpcResult.paramError("[price] is inValid");
        }
        if (!AddressTool.validAddress(chainId, agentAddress)) {
            return RpcResult.paramError("[agentAddress] is inValid");
        }
//        if()
        return null;
    }

}
