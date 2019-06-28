package io.nuls.model.form;

import io.nuls.core.rpc.model.ApiModel;
import io.nuls.core.rpc.model.ApiModelProperty;

/**
 * @Author: zhoulijun
 * @Time: 2019-03-07 15:11
 * @Description: 功能描述
 */
@ApiModel
public class Base {

    @ApiModelProperty(description = "链ID")
    private Integer chainId;

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }
}
