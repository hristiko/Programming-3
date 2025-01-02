package org.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public static String toSHA256(String password){
        try {
            MessageDigest passwordToSHA256 = MessageDigest.getInstance("SHA-256");

            byte[] messageDigest = passwordToSHA256.digest(password.getBytes());

            BigInteger num = new BigInteger(1, messageDigest);

            String hashText = num.toString(16);
            while (hashText.length() < 64){
                hashText = "0" + hashText;
            }
            return hashText;

        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

    }

}
