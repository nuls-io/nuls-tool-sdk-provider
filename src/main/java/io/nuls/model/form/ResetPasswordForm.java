package io.nuls.model.form;

import io.nuls.core.rpc.model.ApiModel;

@ApiModel(description = "离线获取明文私钥表单")
public class ResetPasswordForm {

    private String address;

    private String encryptedPriKey;

    private String oldPassword;

    private String newPassword;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEncryptedPriKey() {
        return encryptedPriKey;
    }

    public void setEncryptedPriKey(String encryptedPriKey) {
        this.encryptedPriKey = encryptedPriKey;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


}
