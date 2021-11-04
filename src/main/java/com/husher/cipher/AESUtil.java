package com.husher.cipher;

import org.apache.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @ClassName AESUtil
 * @Description  AES加解密算法
 * @author qindc
 * @date  2020/7/27 10:01
 *
 */
public class AESUtil {
    private static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    /**
     * 编码格式
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 加密算法
     */
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 签名算法
     */
    private static final String SIGN_ALGORITHMS = "SHA1PRNG";
    /**
     * @author qindc
     * @date 2020/7/27 9:55
     * @Description   加密算法
     * @param   encodeRules  密钥
     * @param   content   需要加密数据
     * @return   加密后数据
     *
     */
    public static String AESEncode(String encodeRules, String content){
        if(StringUtils.isBlank(content)){
            return content;
        }
        content= content.trim();
        try {
            //1.构造秘钥生成器，指定秘钥算法为AES算法，这里不区分大小写
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);//固定签名算法，不然win和linux会有差别
            random.setSeed(encodeRules.getBytes(ENCODING));
            //2.根据encodeRules初始化秘钥生成器，生成一个128位随机源
            //这里分为多种的方式生成秘钥生成器，这里我使用的是：使用用户提供的随机源初始化此密钥生成器，使其具有确定的密钥大小。
            keyGenerator.init(128, random);
            //3.生成原始对称秘钥，这是一个接口类，
            SecretKey secretKey = keyGenerator.generateKey();
            //4.获得原始对称秘钥的字节数组
            byte[] secreKeyByte = secretKey.getEncoded();
            //5.根据字节数组生成AES秘钥
            SecretKey key = new SecretKeySpec(secreKeyByte,KEY_ALGORITHM);
            //6.根据指定算法AES制成密码器
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的key
            cipher.init(Cipher.ENCRYPT_MODE,key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] contentByte = content.getBytes(ENCODING);
            //9.使用密码器进行加密操作
            byte[] byte_AES = cipher.doFinal(contentByte);
            //10.将加密后的字符数组转成Base64的字符格式
            //String key_content_base64 = DatatypeConverter.printBase64Binary(byte_AES);  解决兼容性问题
            BASE64Encoder encoder = new BASE64Encoder();
            String key_content_base64 = encoder.encode(byte_AES);
            return key_content_base64;
        }catch (Exception e){
            logger.error("AES加密出错" ,e);
        }
        return null;

    }

    /**
     * @author qindc
     * @date 2020/7/27 9:58
     * @Description  解密算法
     * @param   encodeRules  密钥
     * @param   content   需要加密数据
     * @return   加密后数据
     *
     */
    public static String AESDecode(String encodeRules, String content){
        //以前老系统值为空会返回$
        if(StringUtils.isBlank(content) || "$".equals(content)){
            return content;
        }
        try {
            //构造秘钥生成器
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(encodeRules.getBytes(ENCODING));
            //初始化秘钥生成器
            keyGenerator.init(128,random);
            //生成原始对称秘钥
            SecretKey secretKey = keyGenerator.generateKey();
            //将原始对称秘钥转成byte数组
            byte[] secretKeyByte = secretKey.getEncoded();
            //根据指定算法AES和数组生成AES秘钥
            SecretKey key = new SecretKeySpec(secretKeyByte,KEY_ALGORITHM);
            //指定AES算法生成密码器      Cipher
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            //初始化密码器
            cipher.init(Cipher.DECRYPT_MODE,key);
            //获取Base64的字符串，进行Base64反编译
            //byte[] base_Decrypt = DatatypeConverter.parseBase64Binary(content);  //解决兼容性问题
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] base_Decrypt = decoder.decodeBuffer(content);
            //使用密码器进行解密
            byte[] decrypt_byte = cipher.doFinal(base_Decrypt);
            //把数组转成string字符串
            String  str = new String(decrypt_byte,ENCODING);
            return str;
        }catch (Exception e){
            logger.error("AES解密出错" ,e);
        }
        return null;
    }
}
