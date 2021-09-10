package com.husher.cipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 1 Cipher类提供了加密和解密的功能。
 该项目使用Cipher类完成aes，des，des3和rsa加密.

 获取Cipher类的对象：Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); 参数按"算法/模式/填充模式"，有以下的参数

 * AES/CBC/NoPadding (128)
 * AES/CBC/PKCS5Padding (128)
 * AES/ECB/NoPadding (128)
 * AES/ECB/PKCS5Padding (128)
 * DES/CBC/NoPadding (56)
 * DES/CBC/PKCS5Padding (56)
 * DES/ECB/NoPadding (56)
 * DES/ECB/PKCS5Padding (56)
 * DESede/CBC/NoPadding (168)
 * DESede/CBC/PKCS5Padding (168)
 * DESede/ECB/NoPadding (168)
 * DESede/ECB/PKCS5Padding (168)
 * RSA/ECB/PKCS1Padding (1024, 2048)
 * RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048)
 * RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048)
 (1)加密算法有：AES，DES，DESede(DES3)和RSA 四种
 (2) 模式有CBC(有向量模式)和ECB(无向量模式)，向量模式可以简单理解为偏移量，使用CBC模式需要定义一个IvParameterSpec对象
 (3) 填充模式:
 * NoPadding: 加密内容不足8位用0补足8位, Cipher类不提供补位功能，需自己实现代码给加密内容添加0, 如{65,65,65,0,0,0,0,0}
 * PKCS5Padding: 加密内容不足8位用余位数补足8位, 如{65,65,65,5,5,5,5,5}或{97,97,97,97,97,97,2,2}; 刚好8位补8位8

 2 Cipher对象需要初始化
 init(int opmode, Key key, AlgorithmParameterSpec params)
 (1)opmode ：Cipher.ENCRYPT_MODE(加密模式)和 Cipher.DECRYPT_MODE(解密模式)
 (2)key ：密匙，使用传入的盐构造出一个密匙，可以使用SecretKeySpec、KeyGenerator和KeyPairGenerator创建密匙，其中
 * SecretKeySpec和KeyGenerator支持AES，DES，DESede三种加密算法创建密匙
 * KeyPairGenerator支持RSA加密算法创建密匙
 (3)params ：使用CBC模式时必须传入该参数，该项目使用IvParameterSpec创建iv 对象

 3 加密或解密
 byte[] b = cipher.doFinal(content);
 返回结果为byte数组，如果直接使用 new String(b) 封装成字符串，则会出现乱码
 */
public class CipherTest {
    /**
     * content: 加密内容
     * slatKey: 加密的盐，16位字符串
     * vectorKey: 加密的向量，16位字符串
     */
    public static String encrypt(String content, String slatKey, String vectorKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * content: 解密内容(base64编码格式)
     * slatKey: 加密时使用的盐，16位字符串
     * vectorKey: 加密时使用的向量，16位字符串
     */
    public static String decrypt(String base64Content, String slatKey, String vectorKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] content = Base64.getDecoder().decode(base64Content);
        byte[] encrypted = cipher.doFinal(content);
        return new String(encrypted);
    }
    /**
     * 获取加密的密匙，传入的slatKey可以是任意长度的，作为SecureRandom的随机种子，
     * 而在KeyGenerator初始化时设置密匙的长度128bit(16位byte)
     */
    private static Key getSlatKey(String slatKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(slatKey.getBytes());
        kgen.init(128, random);
        Key key = kgen.generateKey();
        return key;
    }
    /**
     * 根据slatKey获取公匙，传入的slatKey作为SecureRandom的随机种子
     * 若使用new SecureRandom()创建公匙，则需要记录下私匙，解密时使用
     */
    private static byte[] getPublicKey(String slatKey) throws Exception {
        KeyPairGenerator keyPairGenerator  = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(slatKey.getBytes());
        //or 2048
        keyPairGenerator.initialize(1024, random);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair.getPublic().getEncoded();
    }

    /**
     * 根据slatKey获取私匙，传入的slatKey作为SecureRandom的随机种子
     */
    private static byte[] getPrivateKey(String slatKey) throws Exception {
        KeyPairGenerator keyPairGenerator  = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(slatKey.getBytes());
        // or 2048
        keyPairGenerator.initialize(1024, random);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair.getPrivate().getEncoded();
    }

    public static void main(String[] args) throws Exception {
        String uuid = "1234567812345687";
        System.out.println(uuid);
        String en = encrypt("123456", uuid,uuid);
        System.out.println(en);
        String de = decrypt(en,uuid,uuid);
        System.out.println(de);
    }
}
