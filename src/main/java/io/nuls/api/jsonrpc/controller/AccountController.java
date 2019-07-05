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
import io.nuls.base.api.provider.ServiceManager;
import io.nuls.base.api.provider.account.AccountService;
import io.nuls.base.api.provider.account.facade.CreateAccountReq;
import io.nuls.base.api.provider.account.facade.ImportAccountByKeyStoreReq;
import io.nuls.base.api.provider.account.facade.ImportAccountByPrivateKeyReq;
import io.nuls.base.api.provider.account.facade.UpdatePasswordReq;
import io.nuls.base.basic.AddressTool;
import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Controller;
import io.nuls.core.core.annotation.RpcMethod;
import io.nuls.core.crypto.HexUtil;
import io.nuls.core.model.FormatValidUtils;
import io.nuls.core.model.StringUtils;
import io.nuls.core.rpc.model.*;
import io.nuls.model.jsonrpc.RpcResult;
import io.nuls.model.jsonrpc.RpcResultError;
import io.nuls.rpctools.AccountTools;
import io.nuls.rpctools.LegderTools;
import io.nuls.rpctools.vo.AccountBalance;
import io.nuls.utils.VerifyUtils;
import io.nuls.v2.model.annotation.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Niels
 */
@Controller
public class AccountController {

    @Autowired
    private LegderTools legderTools;
    @Autowired
    private AccountTools accountTools;

    AccountService accountService = ServiceManager.get(AccountService.class);

    @RpcMethod("createAccount")
    @ApiOperation(description = "批量创建账户")
    @Parameters(value = {
            @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链ID"),
            @Parameter(parameterName = "count", requestType = @TypeDescriptor(value = int.class), parameterDes = "创建数量"),
            @Parameter(parameterName = "password", requestType = @TypeDescriptor(value = String.class), parameterDes = "密码")
    })
    @ResponseData(name = "返回值", description = "返回账户地址集合", responseType = @TypeDescriptor(value = List.class))
    public RpcResult createAccount(List<Object> params) {
        VerifyUtils.verifyParams(params, 3);
        int chainId, count;
        String password;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            count = (int) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[count] is inValid");
        }
        try {
            password = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[password] is inValid");
        }

        CreateAccountReq req = new CreateAccountReq(count, password);
        req.setChainId(chainId);
        Result<String> result = accountService.createAccount(req);
        RpcResult rpcResult = new RpcResult();
        if (result.isFailed()) {
            rpcResult.setError(new RpcResultError(result.getStatus(), result.getMessage(), null));
        } else {
            rpcResult.setResult(result.getList());
        }
        return rpcResult;
    }

    @RpcMethod("updatePassword")
    @ApiOperation(description = "重置账密码")
    @Parameters(value = {
            @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链ID"),
            @Parameter(parameterName = "address", requestType = @TypeDescriptor(value = String.class), parameterDes = "账户地址"),
            @Parameter(parameterName = "oldPassword", requestType = @TypeDescriptor(value = String.class), parameterDes = "账户密码"),
            @Parameter(parameterName = "newPassword", requestType = @TypeDescriptor(value = String.class), parameterDes = "新密码")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", valueType = Boolean.class, description = "是否修改成功")
    }))
    public RpcResult updatePassword(List<Object> params) {
        VerifyUtils.verifyParams(params, 4);
        int chainId;
        String address, oldPassword, newPassword;
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
            oldPassword = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[oldPassword] is inValid");
        }
        try {
            newPassword = (String) params.get(3);
        } catch (Exception e) {
            return RpcResult.paramError("[newPassword] is inValid");
        }

        if (!AddressTool.validAddress(chainId, address)) {
            return RpcResult.paramError("[address] is inValid");
        }
        if (!FormatValidUtils.validPassword(oldPassword)) {
            return RpcResult.paramError("[oldPassword] is inValid");
        }
        if (!FormatValidUtils.validPassword(newPassword)) {
            return RpcResult.paramError("[newPassword] is inValid");
        }
        UpdatePasswordReq req = new UpdatePasswordReq(address, oldPassword, newPassword);
        req.setChainId(chainId);
        Result<Boolean> result = accountService.updatePassword(req);
        RpcResult rpcResult = new RpcResult();
        if (result.isSuccess()) {
            rpcResult.setResult(result.getData());
        } else {
            rpcResult.setError(new RpcResultError(result.getStatus(), result.getMessage(), null));
        }
        return rpcResult;
    }


    @RpcMethod("importPriKey")
    @ApiOperation(description = "根据私钥导入账户")
    @Parameters(value = {
            @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链ID"),
            @Parameter(parameterName = "priKey", requestType = @TypeDescriptor(value = String.class), parameterDes = "账户明文私钥"),
            @Parameter(parameterName = "password", requestType = @TypeDescriptor(value = String.class), parameterDes = "新密码")
    })
    @ResponseData(name = "返回值", description = "返回账户地址", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "账户地址")
    }))
    public RpcResult importPriKey(List<Object> params) {
        VerifyUtils.verifyParams(params, 3);
        int chainId;
        String priKey, password;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            priKey = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[priKey] is inValid");
        }
        try {
            password = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[password] is inValid");
        }
        if (StringUtils.isBlank(priKey)) {
            return RpcResult.paramError("[priKey] is inValid");
        }
        if (!FormatValidUtils.validPassword(password)) {
            return RpcResult.paramError("[password] is inValid");
        }

        ImportAccountByPrivateKeyReq req = new ImportAccountByPrivateKeyReq(password, priKey, true);
        req.setChainId(chainId);
        Result<String> result = accountService.importAccountByPrivateKey(req);
        RpcResult rpcResult = new RpcResult();
        if (result.isSuccess()) {
            rpcResult.setResult(result.getData());
        } else {
            rpcResult.setError(new RpcResultError(result.getStatus(), result.getMessage(), null));
        }
        return rpcResult;
    }

    @RpcMethod("importKeystore")
    @ApiOperation(description = "根据keystore导入账户")
    @Parameters(value = {
            @Parameter(parameterName = "chainId", requestType = @TypeDescriptor(value = int.class), parameterDes = "链ID"),
            @Parameter(parameterName = "keyStoreJson", requestType = @TypeDescriptor(value = String.class), parameterDes = "keyStore字符串"),
            @Parameter(parameterName = "password", requestType = @TypeDescriptor(value = String.class), parameterDes = "keystore密码")
    })
    @ResponseData(name = "返回值", description = "返回账户地址", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "账户地址")
    }))
    public RpcResult importKeystore(List<Object> params) {
        VerifyUtils.verifyParams(params, 3);
        int chainId;
        String keyStoreJson, password;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            keyStoreJson = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[keyStoreJson] is inValid");
        }
        try {
            password = (String) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[password] is inValid");
        }
        if (StringUtils.isBlank(keyStoreJson)) {
            return RpcResult.paramError("[keyStoreJson] is inValid");
        }
        if (!FormatValidUtils.validPassword(password)) {
            return RpcResult.paramError("[password] is inValid");
        }

        ImportAccountByKeyStoreReq req = new ImportAccountByKeyStoreReq(password, HexUtil.encode(keyStoreJson.getBytes()), true);
        req.setChainId(chainId);
        Result<String> result = accountService.importAccountByKeyStore(req);
        RpcResult rpcResult = new RpcResult();
        if (result.isSuccess()) {
            rpcResult.setResult(result.getData());
        } else {
            rpcResult.setError(new RpcResultError(result.getStatus(), result.getMessage(), null));
        }
        return rpcResult;
    }

    @RpcMethod("exportKeystore")
    public RpcResult exportKeystore(List<Object> params) {
        VerifyUtils.verifyParams(params, 3);
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
        return null;


    }

    @RpcMethod("getAccountBalance")
    public RpcResult getAccountBalance(List<Object> params) {
        VerifyUtils.verifyParams(params, 4);
        int chainId, assetChainId, assetId;
        String address;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            assetChainId = (int) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[assetChainId] is inValid");
        }
        try {
            assetId = (int) params.get(2);
        } catch (Exception e) {
            return RpcResult.paramError("[assetId] is inValid");
        }
        try {
            address = (String) params.get(3);
        } catch (Exception e) {
            return RpcResult.paramError("[address] is inValid");
        }
        if (!AddressTool.validAddress(chainId, address)) {
            return RpcResult.paramError("[address] is inValid");
        }

        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        RpcResult rpcResult = new RpcResult();
        Result<AccountBalance> balanceResult = legderTools.getBalanceAndNonce(chainId, address, assetChainId, assetId);
        if (balanceResult.isFailed()) {
            return rpcResult.setError(new RpcResultError(balanceResult.getStatus(), balanceResult.getMessage(), null));
        }
        return rpcResult.setResult(balanceResult.getData());
    }


}
