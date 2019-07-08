package io.nuls.model.form;

import io.nuls.core.rpc.model.ApiModel;

@ApiModel(description = "离线获取明文私钥表单")
public class GetPriKeyForm {

    private String address;

    private String encryptedPriKey;

    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
