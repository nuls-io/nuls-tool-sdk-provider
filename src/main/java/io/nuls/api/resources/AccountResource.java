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

import com.fasterxml.jackson.core.JsonProcessingException;
import io.nuls.api.config.Context;
import io.nuls.base.api.provider.Result;
import io.nuls.base.api.provider.ServiceManager;
import io.nuls.base.api.provider.account.AccountService;
import io.nuls.base.api.provider.account.facade.*;
import io.nuls.core.constant.CommonCodeConstanst;
import io.nuls.core.core.annotation.Component;
import io.nuls.core.crypto.HexUtil;
import io.nuls.core.parse.JSONUtils;
import io.nuls.core.rpc.model.*;
import io.nuls.model.ErrorData;
import io.nuls.model.RpcClientResult;
import io.nuls.model.annotation.Api;
import io.nuls.model.annotation.ApiOperation;
import io.nuls.model.dto.AccountKeyStoreDto;
import io.nuls.model.form.*;
import io.nuls.utils.Log;
import io.nuls.utils.ResultUtil;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.List;
import java.util.Map;

import static io.nuls.api.config.Context.getChainId;

/**
 * @author: PierreLuo
 * @date: 2019-06-27
 */
@Path("/api/account")
@Component
@Api
public class AccountResource {

    AccountService accountService = ServiceManager.get(AccountService.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "批量创建账户")
    @Parameters({
            @Parameter(parameterName = "批量创建账户", parameterDes = "批量创建账户表单", requestType = @TypeDescriptor(value = AccountCreateForm.class))
    })
    @ResponseData(name = "返回值", description = "返回一个Map", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "list", valueType = List.class, valueElement = String.class, description = "交易hash")
    }))
    public RpcClientResult create(AccountCreateForm form) {
        if (form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        CreateAccountReq req = new CreateAccountReq(form.getCount(), form.getPassword());
        req.setChainId(form.getChainId());
        Result<String> result = accountService.createAccount(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("list", clientResult.getData()).mapToData();
        }
        return clientResult;
    }

    @PUT
    @Path("/password/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "[修改密码] 根据原密码修改账户密码")
    @Parameters({
            @Parameter(parameterName = "address", parameterDes = "账户地址"),
            @Parameter(parameterName = "账户密码信息", parameterDes = "账户密码信息表单", requestType = @TypeDescriptor(value = AccountUpdatePasswordForm.class))
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", valueType = Boolean.class, description = "是否修改成功")
    }))
    public RpcClientResult updatePassword(@PathParam("address") String address, AccountUpdatePasswordForm form) {
        if (address == null || form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        UpdatePasswordReq req = new UpdatePasswordReq(address, form.getPassword(), form.getNewPassword());
        req.setChainId(getChainId());
        Result<Boolean> result = accountService.updatePassword(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("value", clientResult.getData()).mapToData();
        }
        return clientResult;
    }

    @POST
    @Path("/import/pri")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "根据私钥导入账户")
    @Parameters({
            @Parameter(parameterName = "根据私钥导入账户", parameterDes = "根据私钥导入账户表单", requestType = @TypeDescriptor(value = AccountPriKeyPasswordForm.class))
    })
    @ResponseData(name = "返回值", description = "返回账户地址", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "账户地址")
    }))
    public RpcClientResult create(AccountPriKeyPasswordForm form) {
        if (form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        ImportAccountByPrivateKeyReq req = new ImportAccountByPrivateKeyReq(form.getPassword(), form.getPriKey(), form.getOverwrite());
        req.setChainId(getChainId());
        Result<String> result = accountService.importAccountByPrivateKey(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("value", clientResult.getData()).mapToData();
        }
        return clientResult;
    }

    @POST
    @Path("/import/keystore")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(description = "根据AccountKeyStore导入账户")
    @Parameters({
            @Parameter(parameterName = "根据私钥导入账户", parameterDes = "根据私钥导入账户表单", requestType = @TypeDescriptor(value = InputStream.class))
    })
    @ResponseData(name = "返回值", description = "返回账户地址", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "账户地址")
    }))
    public RpcClientResult importAccountByKeystoreFile(@FormDataParam("keystore") InputStream in,
                                                       @FormDataParam("password") String password,
                                                       @FormDataParam("overwrite") Boolean overwrite) {
        if(in == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        Result<AccountKeyStoreDto> dtoResult = this.getAccountKeyStoreDto(in);
        if(dtoResult.isFailed()) {
            return RpcClientResult.getFailed(new ErrorData(dtoResult.getStatus(), dtoResult.getMessage()));
        }
        AccountKeyStoreDto dto = dtoResult.getData();
        try {
            ImportAccountByKeyStoreReq req = new ImportAccountByKeyStoreReq(password, HexUtil.encode(JSONUtils.obj2json(dto).getBytes()), overwrite);
            req.setChainId(getChainId());
            Result<String> result = accountService.importAccountByKeyStore(req);
            RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
            if(clientResult.isSuccess()) {
                return clientResult.resultMap().map("value", clientResult.getData()).mapToData();
            }
            return clientResult;
        } catch (JsonProcessingException e) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.DATA_ERROR.getCode(), e.getMessage()));
        }
    }

    @POST
    @Path("/import/keystore/path")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "根据keystore文件路径导入账户")
    @Parameters({
            @Parameter(parameterName = "根据keystore文件路径导入账户", parameterDes = "根据keystore文件路径导入账户表单", requestType = @TypeDescriptor(value = AccountKeyStoreImportForm.class))
    })
    @ResponseData(name = "返回值", description = "返回账户地址", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "账户地址")
    }))
    public RpcClientResult imporAccountByKeystoreFilePath(AccountKeyStoreImportForm form) {
        if(form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        String keystore = accountService.getAccountKeystoreDto(form.getPath());
        ImportAccountByKeyStoreReq req = new ImportAccountByKeyStoreReq(form.getPassword(), HexUtil.encode(keystore.getBytes()), form.getOverwrite());
        req.setChainId(getChainId());
        Result<String> result = accountService.importAccountByKeyStore(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("value", clientResult.getData()).mapToData();
        }
        return clientResult;
    }

    @POST
    @Path("/import/keystore/string")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "根据keystore字符串导入账户")
    @Parameters({
            @Parameter(parameterName = "根据keystore字符串导入账户", parameterDes = "根据keystore字符串导入账户表单", requestType = @TypeDescriptor(value = AccountKeyStoreStringImportForm.class))
    })
    @ResponseData(name = "返回值", description = "返回账户地址", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "账户地址")
    }))
    public RpcClientResult imporAccountByKeystoreString(AccountKeyStoreStringImportForm form) {
        if(form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        String keystore = form.getKeystoreString();
        ImportAccountByKeyStoreReq req = new ImportAccountByKeyStoreReq(form.getPassword(), HexUtil.encode(keystore.getBytes()), form.getOverwrite());
        req.setChainId(getChainId());
        Result<String> result = accountService.importAccountByKeyStore(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("value", clientResult.getData()).mapToData();
        }
        return clientResult;
    }

    @POST
    @Path("/export/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "账户备份，导出AccountKeyStore文件到指定目录")
    @Parameters({
            @Parameter(parameterName = "address", parameterDes = "账户地址", requestType = @TypeDescriptor(value = String.class)),
            @Parameter(parameterName = "keystone导出信息", parameterDes = "keystone导出信息表单", requestType = @TypeDescriptor(value = AccountKeyStoreBackup.class))
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "path", description = "导出的文件路径")
    }))
    public RpcClientResult exportAccountKeyStore(@PathParam("address") String address, AccountKeyStoreBackup form) {
        if (address == null || form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        BackupAccountReq req = new BackupAccountReq(form.getPassword(), address, form.getPath());
        req.setChainId(getChainId());
        Result<String> result = accountService.backupAccount(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("path", clientResult.getData()).mapToData();
        }
        return clientResult;
    }

    @POST
    @Path("/prikey/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "账户备份，导出账户私钥，只能导出本地创建或导入的账户")
    @Parameters({
            @Parameter(parameterName = "address", parameterDes = "账户地址"),
            @Parameter(parameterName = "账户密码信息", parameterDes = "账户密码信息表单", requestType = @TypeDescriptor(value = AccountPasswordForm.class))
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = Map.class, mapKeys = {
            @Key(name = "value", description = "私钥")
    }))
    public RpcClientResult getPrikey(@PathParam("address") String address, AccountPasswordForm form) {
        if (address == null || form == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        GetAccountPrivateKeyByAddressReq req = new GetAccountPrivateKeyByAddressReq(form.getPassword(), address);
        req.setChainId(getChainId());
        Result<String> result = accountService.getAccountPrivateKey(req);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if(clientResult.isSuccess()) {
            return clientResult.resultMap().map("value", clientResult.getData()).mapToData();
        }
        return clientResult;
    }

    private Result<AccountKeyStoreDto> getAccountKeyStoreDto(InputStream in) {
        StringBuilder ks = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String str;
        try {
            inputStreamReader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((str = bufferedReader.readLine()) != null) {
                if (!str.isEmpty()) {
                    ks.append(str);
                }
            }
            AccountKeyStoreDto accountKeyStoreDto = JSONUtils.json2pojo(ks.toString(), AccountKeyStoreDto.class);
            return new Result(accountKeyStoreDto);
        } catch (Exception e) {
            return Result.fail(CommonCodeConstanst.FILE_OPERATION_FAILD.getCode(), "key store file error");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.error(e);
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    Log.error(e);
                }
            }
        }
    }
}
