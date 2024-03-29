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

package io.nuls.model.dto;


import io.nuls.base.basic.AddressTool;
import io.nuls.base.data.CoinTo;
import io.nuls.core.rpc.model.ApiModel;
import io.nuls.core.rpc.model.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.nuls.v2.util.ContractUtil.bigInteger2String;


/**
 * @author: PierreLuo
 * @date: 2019-03-14
 */
@Data
@NoArgsConstructor
@ApiModel
public class OutputDto {
    @ApiModelProperty(description = "输出地址")
    private String address;
    @ApiModelProperty(description = "资产链ID")
    private int assetsChainId;
    @ApiModelProperty(description = "资产ID")
    private int assetsId;
    @ApiModelProperty(description = "输出金额")
    private String amount;
    @ApiModelProperty(description = "锁定时间")
    private long lockTime;

    public OutputDto(CoinTo to) {
        this.address = AddressTool.getStringAddressByBytes(to.getAddress());
        this.assetsChainId = to.getAssetsChainId();
        this.assetsId = to.getAssetsId();
        this.amount = bigInteger2String(to.getAmount());
        this.lockTime = to.getLockTime();
    }

}
