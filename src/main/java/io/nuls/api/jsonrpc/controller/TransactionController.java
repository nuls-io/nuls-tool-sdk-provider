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
package io.nuls.api.jsonrpc.controller;

import io.nuls.api.config.Context;
import io.nuls.base.RPCUtil;
import io.nuls.base.api.provider.Result;
import io.nuls.base.basic.AddressTool;
import io.nuls.base.basic.NulsByteBuffer;
import io.nuls.base.data.Transaction;
import io.nuls.core.constant.CommonCodeConstanst;
import io.nuls.core.constant.ErrorCode;
import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Controller;
import io.nuls.core.core.annotation.RpcMethod;
import io.nuls.core.model.StringUtils;
import io.nuls.model.jsonrpc.RpcErrorCode;
import io.nuls.model.jsonrpc.RpcResult;
import io.nuls.model.txdata.CallContractData;
import io.nuls.model.txdata.CreateContractData;
import io.nuls.model.txdata.DeleteContractData;
import io.nuls.rpctools.ContractTools;
import io.nuls.rpctools.TransactionTools;
import io.nuls.utils.Log;
import io.nuls.utils.VerifyUtils;

import java.util.List;
import java.util.Map;

import static io.nuls.core.constant.TxType.*;
import static io.nuls.utils.Utils.extractTxTypeFromTx;

/**
 * @author: PierreLuo
 * @date: 2019-07-02
 */
@Controller
public class TransactionController {

    @Autowired
    private TransactionTools transactionTools;
    @Autowired
    private ContractTools contractTools;

    @RpcMethod("validateTx")
    public RpcResult validateTx(List<Object> params) {
        VerifyUtils.verifyParams(params, 2);
        int chainId;
        String txHex;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            txHex = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[txHex] is inValid");
        }
        if (!Context.isChainExist(chainId)) {
            return RpcResult.dataNotFound();
        }
        if (StringUtils.isBlank(txHex)) {
            return RpcResult.paramError("[txHex] is inValid");
        }
        Result result = transactionTools.validateTx(chainId, txHex);
        if (result.isSuccess()) {
            return RpcResult.success(result.getData());
        } else {
            return RpcResult.failed(ErrorCode.init(result.getStatus()), result.getMessage());
        }
    }

    @RpcMethod("broadcastTx")
    public RpcResult broadcastTx(List<Object> params) {
        VerifyUtils.verifyParams(params, 2);
        int chainId;
        String txHex;
        try {
            chainId = (int) params.get(0);
        } catch (Exception e) {
            return RpcResult.paramError("[chainId] is inValid");
        }
        try {
            txHex = (String) params.get(1);
        } catch (Exception e) {
            return RpcResult.paramError("[txHex] is inValid");
        }

        try {
            if (!Context.isChainExist(chainId)) {
                return RpcResult.dataNotFound();
            }
            int type = extractTxTypeFromTx(txHex);
            Result result = null;
            switch (type) {
                case CREATE_CONTRACT:
                    Transaction tx = new Transaction();
                    tx.parse(new NulsByteBuffer(RPCUtil.decode(txHex)));
                    CreateContractData create = new CreateContractData();
                    create.parse(new NulsByteBuffer(tx.getTxData()));
                    result = contractTools.validateContractCreate(chainId,
                            AddressTool.getStringAddressByBytes(create.getSender()),
                            create.getGasLimit(),
                            create.getPrice(),
                            RPCUtil.encode(create.getCode()),
                            create.getArgs());
                    break;
                case CALL_CONTRACT:
                    Transaction callTx = new Transaction();
                    callTx.parse(new NulsByteBuffer(RPCUtil.decode(txHex)));
                    CallContractData call = new CallContractData();
                    call.parse(new NulsByteBuffer(callTx.getTxData()));
                    result = contractTools.validateContractCall(chainId,
                            AddressTool.getStringAddressByBytes(call.getSender()),
                            call.getValue(),
                            call.getGasLimit(),
                            call.getPrice(),
                            AddressTool.getStringAddressByBytes(call.getContractAddress()),
                            call.getMethodName(),
                            call.getMethodDesc(),
                            call.getArgs());
                    break;
                case DELETE_CONTRACT:
                    Transaction deleteTx = new Transaction();
                    deleteTx.parse(new NulsByteBuffer(RPCUtil.decode(txHex)));
                    DeleteContractData delete = new DeleteContractData();
                    delete.parse(new NulsByteBuffer(deleteTx.getTxData()));
                    result = contractTools.validateContractDelete(chainId,
                            AddressTool.getStringAddressByBytes(delete.getSender()),
                            AddressTool.getStringAddressByBytes(delete.getContractAddress()));
                    break;
                default:
                    break;
            }
            Map contractMap = (Map) result.getData();
            if (contractMap != null && Boolean.FALSE.equals(contractMap.get("success"))) {
                return RpcResult.failed(CommonCodeConstanst.DATA_ERROR, (String) contractMap.get("msg"));
            }

            result = transactionTools.newTx(chainId, txHex);

            if (result.isSuccess()) {
                return RpcResult.success(result.getData());
            } else {
                return RpcResult.failed(ErrorCode.init(result.getStatus()), result.getMessage());
            }
        } catch (Exception e) {
            Log.error(e);
            return RpcResult.failed(RpcErrorCode.TX_PARSE_ERROR);
        }
    }
}
