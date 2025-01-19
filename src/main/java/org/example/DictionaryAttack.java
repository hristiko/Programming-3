package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class DictionaryAttack {

    private ArrayList<String> commonPasswords = new ArrayList<String>();
    private ArrayList<String> commonPasswordsHashed = new ArrayList<String>();

    boolean typeHashMD5;

    DictionaryAttack(boolean typeHashMD5) {
        this.typeHashMD5 = typeHashMD5;
    }

    public void DictionaryAttackFun(){
        Path fileName = Path.of("D:\\H.K\\FAX\\Prog3\\Project\\Pass1\\prj\\src\\main\\java\\PasswordDictionary.txt");

        try (BufferedReader buffer = new BufferedReader(new FileReader(String.valueOf(fileName)))){
            String str;
            while ((str = buffer.readLine()) != null){
                commonPasswords.add(str);
                if (typeHashMD5){
                    String strToHash = MD5.toMD5crack(str);
                    commonPasswordsHashed.add(strToHash);
                } else {
                    String strToHash = SHA256.toSHA256(str);
                    commonPasswordsHashed.add(strToHash);
                }

            }
        }

        catch (IOException e){
            e.printStackTrace();
        }
    }

    /*public void setCommonPasswords(ArrayList<String> commonPasswordsHashed) {
        this.commonPasswordsHashed = commonPasswordsHashed;
    }*/

    public ArrayList<String> getCommonPasswordsHashed() {
        return commonPasswordsHashed;
    }

    public ArrayList<String> getCommonPasswords() {
        return commonPasswords;
    }
}
