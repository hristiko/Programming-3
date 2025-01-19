package org.example;

import util.LogLevel;
import util.Logger;

public class Helper {

    public boolean targetMD5 = false;
    public boolean targetSHA256 = false;
    public boolean UpperCase = false;
    public boolean LowerCase = false;
    public boolean Digits = false;
    public boolean SpecialChars = false;
    public boolean attackTypeBF = false;
    public boolean attackTypeDA = false;
    public int maxLength;

    public static String pass;

    private boolean start;

    public Helper(){

        long startTime = System.currentTimeMillis();

        Gui window = new Gui();
        //DictionaryAttack da = new DictionaryAttack();

        while (!window.isStart()){
            try {
                Thread.sleep(100); // Small delay to avoid busy-waiting
            } catch (InterruptedException e) {
                Logger.log("Waiting...", LogLevel.Status);
            }
        }

        start = window.isStart();
        setStart(start);
        Logger.log("Helper STARTED: " + isWindowStart(), LogLevel.Status);



        //Logger.log("HELPER: MD5 " + window.isTargetMD5());
        if (window.isTargetMD5()){
            Logger.log("HELPER: MD5 TRUE");
            targetMD5 = true;
            setTargetMD5(targetMD5);
        }

        if (window.isCharacterSetSpecialCharacters()){
            //bf.setCharacterSetSpecialCharacters(true);
            SpecialChars = true;
            setSpecialChars(SpecialChars);
        }


        if (window.isCharacterSetDigits()){
            Logger.log("HELPER: DIGITS TRUE");
            Digits = true;
            setDigits(Digits);
        }

        if (window.isCharacterSetUpperCase()){
            UpperCase = true;
            setUpperCase(UpperCase);
        }

        if (window.isCharacterSetLowerCase()){
            LowerCase = true;
            setLowerCase(LowerCase);
        }


        pass = window.getPasswordInput();
        Logger.log("HELPER: "+ pass);
        maxLength = window.getMaxPasswordLength();
        setMaxLength(maxLength);
        Logger.log("HELPER maxLength: "+ getMaxLength());


        //Logger.log("BruteForceCracking START set to: " + isWindowStart(), LogLevel.Status);


        if (window.isBfType()){
            BruteForce bruteForce = new BruteForce(pass, getMaxLength(), isUpperCase(), isLowerCase(), isDigits(), isSpecialChars(), isTargetMD5(), isWindowStart());
            bruteForce.BruteForceCracking();

            Logger.log("Size: " + bruteForce.getCandidatePasswordsHashed().size());
            Logger.log("STARTED CRACKING");
            double increment = 100. /bruteForce.getCandidatePasswordsHashed().size();
            double barI = 0;

            for (int i=0; i<bruteForce.getCandidatePasswordsHashed().size(); i++){
                Logger.log("Cracking ... ", LogLevel.Status);
                window.fill(barI);
                barI+=increment;
                Logger.log("Incrementer: " + barI, LogLevel.Status);
                if (pass.equals(bruteForce.getCandidatePasswordsHashed().get(i))){
                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;
                    System.out.println("FOUND: " + bruteForce.getCandidatePasswords().get(i));
                    window.fill(100);
                    window.finalPassword("The password is: " + bruteForce.getCandidatePasswords().get(i) + ". Total time: " + totalTime + " ms.");
                    Logger.log("The password is: " + bruteForce.getCandidatePasswords().get(i) + ". Total time: " + totalTime + " ms.", LogLevel.Success);
                    break;
                }
                else {
                    if (i == bruteForce.getCandidatePasswordsHashed().size()-1){
                        System.out.println("NOT FOUND!");
                    }
                }
            }
        }

        if(window.isDaType()){
            DictionaryAttack dictionaryAttack = new DictionaryAttack(isTargetMD5());
            dictionaryAttack.DictionaryAttackFun();

            double increment = 100. /dictionaryAttack.getCommonPasswordsHashed().size();
            double barI = 0;

            for (int i=0; i<dictionaryAttack.getCommonPasswordsHashed().size(); i++){
                window.fill(barI);
                barI+=increment;
                if (pass.equals(dictionaryAttack.getCommonPasswordsHashed().get(i))){
                    System.out.println("FOUND! " + dictionaryAttack.getCommonPasswords().get(i));
                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;
                    window.fill(100);
                    window.finalPassword("The password is: " + dictionaryAttack.getCommonPasswords().get(i) + ". Total time: " + totalTime + " ms.");
                    Logger.log("The password is: " + dictionaryAttack.getCommonPasswords().get(i) + ". Total time: " + totalTime + " ms.", LogLevel.Success);
                    break;
                } else{
                    if (i == dictionaryAttack.getCommonPasswords().size()-1){
                        System.out.println("NOT FOUND!");
                    }
                }
            }

        }



        //bf.setPassLength(window.getMaxPasswordLength());

        //System.out.println("To SHA256: " + SHA256.toSHA256("12345678"));

        /*System.out.println("To MD5: " + MD5.toMD5crack(pass));*/

        /*for (int i=0; i<ds.getCommonPasswordsHashed().size(); i++){
            if (pass.equals(ds.getCommonPasswordsHashed().get(i))){
                System.out.println("FOUND! " + ds.getCommonPasswords().get(i));
                break;
            } else{
                if (i == ds.getCommonPasswords().size()-1){
                    System.out.println("NOT FOUND!");
                }
            }
        }*/




    }

    public boolean isDigits() {
        return Digits;
    }

    public void setDigits(boolean digits) {
        Digits = digits;
    }

    public boolean isLowerCase() {
        return LowerCase;
    }

    public void setLowerCase(boolean lowerCase) {
        LowerCase = lowerCase;
    }

    public boolean isSpecialChars() {
        return SpecialChars;
    }

    public void setSpecialChars(boolean specialChars) {
        SpecialChars = specialChars;
    }

    public boolean isTargetMD5() {
        return targetMD5;
    }

    public void setTargetMD5(boolean targetMD5) {
        this.targetMD5 = targetMD5;
    }

    public boolean isTargetSHA256() {
        return targetSHA256;
    }

    public void setTargetSHA256(boolean targetSHA256) {
        this.targetSHA256 = targetSHA256;
    }

    public boolean isAttackTypeBF() {
        return attackTypeBF;
    }

    public boolean isAttackTypeDA() {
        return attackTypeDA;
    }

    public void setAttackTypeBF(boolean attackTypeBF) {
        this.attackTypeBF = attackTypeBF;
    }

    public void setAttackTypeDA(boolean attackTypeDA) {
        this.attackTypeDA = attackTypeDA;
    }

    public boolean isUpperCase() {
        return UpperCase;
    }

    public void setUpperCase(boolean upperCase) {
        UpperCase = upperCase;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public boolean isWindowStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
