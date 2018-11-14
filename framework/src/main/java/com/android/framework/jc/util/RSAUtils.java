package com.android.framework.jc.util;

import android.text.TextUtils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/6 10:03
 * @describe RSA相关工具
 * @update
 */
public class RSAUtils {
    private final static String RSA = "RSA";
    private final static String RSA_ECB = "RSA/ECB/PKCS1Padding";
    /**
     * 默认的rsa加密公钥私钥（本地保存数据使用，经过base64加密处理）
     */
    private final static String DEFAULT_PUBLIC_KEY = "LS0tLS1CRUdJTiUyMFBVQkxJQyUyMEtFWS0tLS0tJTBBTUZ3d0RRWUpLb1pJaHZjTkFRRUJCUUFEU3dBd1NBSkJBSUJZWTRUOTV4cGFuQVEyNHIwckFadkJlNndKMVRwTSUwQTN1NXMrRExaVmFSVVBjaUFTODUrb2FXWVMwVWZjQkZWVDVFYno1REdQVkdTUUJVMU16c1Q5eGNDQXdFQUFRJTNEJTNEJTBBLS0tLS1FTkQlMjBQVUJMSUMlMjBLRVktLS0tLQ==";
    private final static String DEFAULT_PRIVATE_KEY = "LS0tLS1CRUdJTiUyMFJTQSUyMFBSSVZBVEUlMjBLRVktLS0tLSUwQU1JSUJPZ0lCQUFKQkFJQllZNFQ5NXhwYW5BUTI0cjByQVp2QmU2d0oxVHBNM3U1cytETFpWYVJVUGNpQVM4NSslMEFvYVdZUzBVZmNCRlZUNUViejVER1BWR1NRQlUxTXpzVDl4Y0NBd0VBQVFKQVpReXlkbEF0OTVybGdZL3hIejFRJTBBQytEZlRVbUVuMTFNbkc0aFVHKzdOU0NjZDF3TklRQU41QnNYWHhlNjJrcFMrYzhZSkhQQzh5VmZrNzI1cU5QayUwQStRSWhBTURHOXpSMFNOQXUrR2dpQlNPcU02N3VjaXYyTkdrZW5oTU1oN2RhajlCN0FpRUFxbS9sMnhvR1JqWjIlMEE4dXNRWGJwTlpTTnR4K0loNTNSV2NRZElNNERWaHhVQ0lESGtERnc5OEE0NDZiOEJkdlVJK29FK1lydnY1eGFCJTBBZndXTkM1NnZTR0w3QWlCdTRWVmNjVlhibjl0S3RmcHp2NXhTUy9aRHI2MzI4Z3k2Zzg1SkUydlc4UUloQUlVRCUwQXZtbUgwMEhUM2luZjZWRzg1RExGYzhqeitIVGtueFFJMUcwcVZxM2YlMEEtLS0tLUVORCUyMFJTQSUyMFBSSVZBVEUlMjBLRVktLS0tLQ==";

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength
     *         密钥长度，范围：512～2048,一般1024
     *
     * @return KeyPair
     */
    public static KeyPair genKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 采用默认公钥字符串加密
     *
     * @param data
     *         需要加密的数据
     *
     * @return 加密后的数据
     */
    public static byte[] encryptDefaultPublic(byte[] data) {
        return encrypt(data, loadPublicKey(DEFAULT_PUBLIC_KEY));
    }

    /**
     * 采用默认公钥加密
     *
     * @param value
     *         需要加密的数据
     *
     * @return 加密后的数据
     */
    public static String encryptDefaultPublic(String value) {
        return encrypt(value, loadPublicKey(DEFAULT_PUBLIC_KEY));
    }

    /**
     * 公钥加密
     *
     * @param value
     *         需要加密的数据
     * @param publicKey
     *         公钥
     *
     * @return 加密后的数据
     */
    public static String encrypt(String value, PublicKey publicKey) {
        byte[] result = null;
        if (!TextUtils.isEmpty(value)) {
            result = encrypt(value.getBytes(), publicKey);
        }
        return Base64Utils.encode(result);
    }

    /**
     * @param data
     *         需要加密的数据
     * @param publicKey
     *         公钥
     *
     * @return 加密后的数据
     */
    public static byte[] encrypt(byte[] data, PublicKey publicKey) {
        byte[] result = null;
        if (data != null) {
            try {
                Cipher cipher = Cipher.getInstance(RSA_ECB);
                // 编码前设定编码方式及密钥
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                // 传入编码数据并返回编码结果
                result = cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 默认私钥字符串私钥解密（必须是默认公钥加密的字符串加密的数据）
     *
     * @param encryptedData
     *         采用默认公钥字符串加密的数据
     *
     * @return 解密后的数据
     */
    public static byte[] decryptDefaultPublic(byte[] encryptedData) {
        return decrypt(encryptedData, loadPrivateKey(DEFAULT_PRIVATE_KEY));
    }

    /**
     * 默认私钥字符串私钥解密（必须是默认公钥加密的字符串加密的数据）
     *
     * @param encryptedString
     *         采用默认公钥字符串加密的字符串
     *
     * @return 解密后的数据
     */
    public static String decryptDefaultPublic(String encryptedString) {
        return decrypt(encryptedString, loadPrivateKey(DEFAULT_PRIVATE_KEY));
    }

    /**
     * 私钥解密
     *
     * @param encryptedString
     *         加密后的数据
     * @param privateKey
     *         私钥
     *
     * @return 解密后的数据
     */
    public static String decrypt(String encryptedString, PrivateKey privateKey) {
        byte[] result = null;
        if (!TextUtils.isEmpty(encryptedString)) {
            result = decrypt(encryptedString.getBytes(), privateKey);
        }
        return Base64Utils.encode(result);
    }

    /**
     * 私钥解密
     *
     * @param encryptedData
     *         加密后的数据
     * @param privateKey
     *         私钥
     *
     * @return 解密后的数据
     */
    public static byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA_ECB);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            result = cipher.doFinal(encryptedData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     *         公钥数据字符串
     *
     * @return PublicKey公钥
     */
    public static PublicKey loadPublicKey(String publicKeyStr) {
        PublicKey result = null;
        if (!TextUtils.isEmpty(publicKeyStr)) {
            try {
                byte[] buffer = Base64Utils.decode(publicKeyStr);
                KeyFactory keyFactory = KeyFactory.getInstance(RSA);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
                result = keyFactory.generatePublic(keySpec);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    /**
     * 从字符串中加载私钥
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     *         私钥数据字符串
     *
     * @return PrivateKey私钥
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) {
        PrivateKey result = null;
        if (!TextUtils.isEmpty(privateKeyStr)) {
            try {
                byte[] buffer = Base64Utils.decode(privateKeyStr);
                // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
                KeyFactory keyFactory = KeyFactory.getInstance(RSA);
                result = keyFactory.generatePrivate(keySpec);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
