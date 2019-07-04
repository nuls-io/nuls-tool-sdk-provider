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
import io.nuls.api.config.Context;
import io.nuls.api.resources.manager.BeanCopierManager;
import io.nuls.base.api.provider.Result;
import io.nuls.base.api.provider.ServiceManager;
import io.nuls.base.api.provider.block.BlockService;
import io.nuls.base.api.provider.block.facade.BlockHeaderData;
import io.nuls.base.api.provider.block.facade.GetBlockHeaderByHashReq;
import io.nuls.base.api.provider.block.facade.GetBlockHeaderByHeightReq;
import io.nuls.base.api.provider.block.facade.GetBlockHeaderByLastHeightReq;
import io.nuls.base.api.provider.transaction.TransferService;
import io.nuls.base.api.provider.transaction.facade.TransferReq;
import io.nuls.base.data.Block;
import io.nuls.core.constant.CommonCodeConstanst;
import io.nuls.core.constant.ErrorCode;
import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Component;
import io.nuls.core.exception.NulsException;
import io.nuls.core.rpc.model.*;
import io.nuls.model.ErrorData;
import io.nuls.model.RpcClientResult;
import io.nuls.model.annotation.Api;
import io.nuls.model.annotation.ApiOperation;
import io.nuls.model.dto.block.BlockDto;
import io.nuls.model.dto.block.BlockHeaderDto;
import io.nuls.model.form.TransferForm;
import io.nuls.rpctools.BlockTools;
import io.nuls.utils.Log;
import io.nuls.utils.ResultUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * @author: PierreLuo
 * @date: 2019-06-27
 */
@Path("/api/block")
@Component
@Api
public class BlockResource {

    @Autowired
    private Config config;

    BlockService blockService = ServiceManager.get(BlockService.class);

    @Autowired
    BlockTools blockTools;

    @GET
    @Path("/header/height/{height}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "根据区块高度查询区块头")
    @Parameters({
            @Parameter(parameterName = "height", requestType = @TypeDescriptor(value = Long.class), parameterDes = "区块高度")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = BlockHeaderDto.class))
    public RpcClientResult getBlockHeaderByHeight(@PathParam("height") Long height) {
        if (height == null || height < 0) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        Result<BlockHeaderData> result = blockService.getBlockHeaderByHeight(new GetBlockHeaderByHeightReq(height));
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if (clientResult.isSuccess() && clientResult.getData() != null) {
            BlockHeaderDto dto = new BlockHeaderDto();
            Object data = clientResult.getData();
            BeanCopierManager.beanCopier("BlockHeaderData2BlockHeaderDto", data, dto);
            clientResult.setData(dto);
        }
        return clientResult;
    }

    @GET
    @Path("/header/hash/{hash}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "根据区块hash查询区块头")
    @Parameters({
            @Parameter(parameterName = "hash", parameterDes = "区块hash")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = BlockHeaderDto.class))
    public RpcClientResult getBlockHeaderByHash(@PathParam("hash") String hash) {
        if (hash == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        Result<BlockHeaderData> result = blockService.getBlockHeaderByHash(new GetBlockHeaderByHashReq(hash));
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if (clientResult.isSuccess() && clientResult.getData() != null) {
            BlockHeaderDto dto = new BlockHeaderDto();
            Object data = clientResult.getData();
            BeanCopierManager.beanCopier("BlockHeaderData2BlockHeaderDto", data, dto);
            clientResult.setData(dto);
        }
        return clientResult;
    }

    @GET
    @Path("/header/newest")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "查询最新区块头信息")
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = BlockHeaderDto.class))
    public RpcClientResult getBestBlockHeader() {
        Result<BlockHeaderData> result = blockService.getBlockHeaderByLastHeight(new GetBlockHeaderByLastHeightReq());
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if (clientResult.isSuccess()) {
            BlockHeaderDto dto = new BlockHeaderDto();
            Object data = clientResult.getData();
            BeanCopierManager.beanCopier("BlockHeaderData2BlockHeaderDto", data, dto);
            clientResult.setData(dto);
        }
        return clientResult;
    }

    @GET
    @Path("/newest")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "查询最新区块")
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = BlockDto.class))
    public RpcClientResult getBestBlock() {
        Result<Block> result = blockTools.getBestBlock(Context.getChainId());
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if (clientResult.isSuccess()) {
            try {
                clientResult.setData(new BlockDto((Block) clientResult.getData()));
            } catch (NulsException e) {
                Log.error(e);
                return ResultUtil.getNulsExceptionRpcClientResult(e);
            }
        }
        return clientResult;
    }

    @GET
    @Path("/height/{height}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "根据区块高度查询区块，包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用")
    @Parameters({
            @Parameter(parameterName = "height", requestType = @TypeDescriptor(value = Long.class), parameterDes = "区块高度")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = BlockHeaderDto.class))
    public RpcClientResult getBlockByHeight(@PathParam("height") Long height) {
        if (height == null || height < 0) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        Result<Block> result = blockTools.getBlockByHeight(Context.getChainId(), height);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if (clientResult.isSuccess()) {
            try {
                if (clientResult.getData() != null) {
                    clientResult.setData(new BlockDto((Block) clientResult.getData()));
                }
            } catch (NulsException e) {
                Log.error(e);
                return ResultUtil.getNulsExceptionRpcClientResult(e);
            }
        }
        return clientResult;
    }

    @GET
    @Path("/hash/{hash}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(description = "根据区块hash查询区块，包含区块打包的所有交易信息，此接口返回数据量较多，谨慎调用")
    @Parameters({
            @Parameter(parameterName = "hash", parameterDes = "区块hash")
    })
    @ResponseData(name = "返回值", responseType = @TypeDescriptor(value = BlockHeaderDto.class))
    public RpcClientResult getBlockByHeight(@PathParam("hash") String hash) {
        if (hash == null) {
            return RpcClientResult.getFailed(new ErrorData(CommonCodeConstanst.PARAMETER_ERROR));
        }
        Result<Block> result = blockTools.getBlockByHash(Context.getChainId(), hash);
        RpcClientResult clientResult = ResultUtil.getRpcClientResult(result);
        if (clientResult.isSuccess()) {
            try {
                if (clientResult.getData() != null) {
                    clientResult.setData(new BlockDto((Block) clientResult.getData()));
                }
            } catch (NulsException e) {
                Log.error(e);
                return ResultUtil.getNulsExceptionRpcClientResult(e);
            }
        }
        return clientResult;
    }

}
