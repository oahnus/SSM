package cn.edu.just.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 */
public class MD5Util {
    private static byte[] encode2bytes(String source){
        byte[] result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(source.getBytes());

            result = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String encode2hex(String source){
        byte[] bytes = encode2bytes(source);

        StringBuffer hexString = new StringBuffer();

        for(int i=0;i<bytes.length;i++){
            String hex = Integer.toHexString(0xff&bytes[i]);

            if(hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

//    public static void main(String[] args){
//        System.out.println(encode2hex("admin"));
//    }
}
