package io.nuls.model.dto;

import io.nuls.base.api.provider.ledger.facade.AccountBalanceInfo;
import io.nuls.core.rpc.model.ApiModel;
import io.nuls.core.rpc.model.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: PierreLuo
 * @date: 2019-06-30
 */
@Data
@NoArgsConstructor
@ApiModel
public class AccountBalanceDto {

    @ApiModelProperty(description = "总余额")
    private String total;
    @ApiModelProperty(description = "锁定金额")
    private String freeze;
    @ApiModelProperty(description = "可用余额")
    private String available;

    public AccountBalanceDto(AccountBalanceInfo info) {
        this.total = info.getTotal().toString();
        this.freeze = info.getFreeze().toString();
        this.available = info.getAvailable().toString();
    }
}
