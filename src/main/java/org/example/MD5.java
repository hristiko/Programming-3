package org.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static String toMD5crack(String password){
        try{
            MessageDigest passwordToMD5 = MessageDigest.getInstance("MD5");

            byte[] messageDigest = passwordToMD5.digest(password.getBytes());

            BigInteger num = new BigInteger(1, messageDigest);

            String hashText = num.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        }

        catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

}
