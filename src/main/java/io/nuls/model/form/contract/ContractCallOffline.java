/*
 * MIT License
 *
 * Copyright (c) 2017-2019 nuls.io
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package io.nuls.model.form.contract;


import io.nuls.core.rpc.model.ApiModel;
import io.nuls.core.rpc.model.ApiModelProperty;
import io.nuls.model.form.Base;
import io.nuls.v2.util.ContractUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @author: PierreLuo
 * @date: 2019-07-04
 */
@Data
@NoArgsConstructor
@ApiModel
public class ContractCallOffline extends Base {

    @ApiModelProperty(description = "交易创建者")
    private String sender;
    @ApiModelProperty(description = "账户余额")
    private BigInteger senderBalance;
    @ApiModelProperty(description = "账户nonce值")
    private String nonce;
    @ApiModelProperty(description = "智能合约地址", required = true)
    private String contractAddress;
    @ApiModelProperty(description = "GAS限制")
    private long gasLimit;
    @ApiModelProperty(description = "调用者向合约地址转入的主网资产金额，没有此业务时填0")
    private BigInteger value;
    @ApiModelProperty(description = "方法名", required = true)
    private String methodName;
    @ApiModelProperty(description = "方法描述，若合约内方法没有重载，则此参数可以为空", required = false)
    private String methodDesc;
    @ApiModelProperty(description = "参数列表", required = false)
    private Object[] args;
    @ApiModelProperty(description = "参数类型列表", required = false)
    private String[] argsType;
    @ApiModelProperty(description = "备注", required = false)
    private String remark;

    public String[][] getArgs(String[] types) {
        return ContractUtil.twoDimensionalArray(args, types);
    }

}
