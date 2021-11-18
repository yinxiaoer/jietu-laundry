package com.jietu.admin.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 用户密码加密工具类
 * @author: 印修河
 * @date: 2017/12/11 14:14
 */
public class PasswordUtil {

    public static String encrypting(String password, String salt){
        String hashAlgorithmName = "MD5";
        String credentials = password;
        int hashIterations = 16;
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
        Object obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations);
        return obj.toString();
    }

    public static void main(String[] args) {
        System.out.println(encrypting("admin", "admin"));
    }
}
