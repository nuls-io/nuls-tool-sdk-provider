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
import io.nuls.core.constant.CommonCodeConstanst;
import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Component;
import io.nuls.core.rpc.model.Parameter;
import io.nuls.core.rpc.model.Parameters;
import io.nuls.core.rpc.model.ResponseData;
import io.nuls.core.rpc.model.TypeDescriptor;
import io.nuls.model.ErrorData;
import io.nuls.model.RpcClientResult;
import io.nuls.v2.model.annotation.Api;
import io.nuls.v2.model.annotation.ApiOperation;
import io.nuls.model.dto.TransactionDto;
import io.nuls.rpctools.TransactionTools;
import io.nuls.utils.ResultUtil;
import io.nuls.v2.util.ValidateUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author: PierreLuo
 * @date: 2019-06-30
 */
@Path("/api/tx")
@Component
@Api
public class TransactionResource {

    @Autowired
    Config config;
    @Autowired
    TransactionTools transactionTools;

    @GET
    @Path("/hash/{hash}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "根据hash获取交易，只查已确认交易")
    @Parameters({
        @Parameter(parameterName = "hash", parameterDes = "交易hash")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = TransactionDto.class))
    public RpcClientResult getTx(@PathParam("hash") String hash) {
        if (hash == null || ValidateUtil.validHash(hash)) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR.getCode(), "hash is invalid"));
        }
        Result<TransactionDto> result = transactionTools.getConfirmedTx(config.getChainId(), hash);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        return clientResult;
    }
}
