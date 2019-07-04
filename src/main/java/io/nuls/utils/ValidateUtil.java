package io.nuls.utils;

import io.nuls.core.crypto.HexUtil;
import io.nuls.core.model.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
//
//    public static void validateChainId() {
//        if (SDKContext.main_chain_id < 1 || SDKContext.main_chain_id > 65535) {
//            throw new RuntimeException("config main_chain_id is invalid");
//        }
//    }

    public static boolean validateChainId(int chainId) {
        return chainId >= 1 && chainId <= 65535;
    }

    public static boolean validateCoinAmount(BigInteger amount) {
        if (amount == null) {
            return false;
        }
        if (amount.longValue() < 0) {
            return false;
        }
        return true;
    }

    public static boolean validateLockTime(long lockTime) {
        return lockTime >= -1;
    }

    /**
     * 校验转账交易备注是否有效
     *
     * @param remark
     * @return
     */
    public static boolean validTxRemark(String remark) {
        if (StringUtils.isBlank(remark)) {
            return true;
        }
        try {
            byte[] bytes = remark.getBytes("UTF-8");
            return bytes.length <= 100;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    public static boolean validateNonce(String nonce) {
        if (StringUtils.isBlank(nonce)) {
            return false;
        }
        return regexMatch(nonce, "^[A-Za-z0-9]{10,20}$");
    }


    public static boolean validateCommissionRate(int commissionRate) {
        return commissionRate >= 0 || commissionRate <= 100;
    }

    public static boolean validHash(String hex) {
        try {
            HexUtil.decode(hex);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validateBigInteger(String value) {
        try {
            new BigInteger(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // 正则验证
    public static boolean regexMatch(String str, String regex) {
        if (str == null) {
            return false;
        }
        boolean flag = false;
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}