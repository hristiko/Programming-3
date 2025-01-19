package org.example;

import util.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Gui {

    private boolean targetMD5 = false;
    private boolean targetSHA5 = false;
    private boolean characterSetUpperCase = false;
    private boolean characterSetLowerCase = false;
    private boolean characterSetDigits = false;
    private boolean characterSetSpecialCharacters = false;
    private boolean bfType = false;
    private boolean daType = false;
    private int maxPasswordLength;
    private String passwordInput;
    private boolean start;

    static JProgressBar crackBar = new JProgressBar(0,100);

    public static JLabel password = new JLabel();

    public Gui() {

        JFrame frame = new JFrame("Brute Force Password Cracker");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(new GridLayout(15, 2));

        Font boldFont = new Font("Arial", Font.BOLD, 14);

        //-----------FIRST ROW----------
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p1.setBackground(Color.DARK_GRAY);

        Label labelSelectTypeOfHash = new Label("Select the type of hash:");
        labelSelectTypeOfHash.setForeground(Color.white);
        labelSelectTypeOfHash.setFont(boldFont);
        p1.add(labelSelectTypeOfHash);

        JRadioButton md5 = new JRadioButton("MD5");
        md5.setForeground(Color.WHITE);
        md5.setBackground(Color.darkGray);

        JRadioButton sha256 = new JRadioButton("SHA256");
        sha256.setForeground(Color.WHITE);
        sha256.setBackground(Color.darkGray);

        ButtonGroup p1Options = new ButtonGroup();
        p1Options.add(md5);
        p1Options.add(sha256);

        p1.add(md5);
        p1.add(sha256);

        md5.addActionListener(e -> {
            setTargetMD5(true);
            setTargetSHA5(false);
            Logger.log("GUI: Pressed MD5: " + isTargetMD5());
        });

        sha256.addActionListener(e -> {
            setTargetSHA5(true);
            setTargetMD5(false);
            Logger.log("Pressed: MD5: " + isTargetMD5() + ". SHA256: " + isTargetSHA256());
        });

        //-----------SECOND ROW--------------
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2.setBackground(Color.DARK_GRAY);

        Label labelSelectCharacterSet = new Label("Select the character set:");
        labelSelectCharacterSet.setForeground(Color.white);
        labelSelectCharacterSet.setFont(boldFont);
        p2.add(labelSelectCharacterSet);

        //-----------THIRD ROW-----------
        JPanel p2Options = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2Options.setBackground(Color.DARK_GRAY);

        JButton lc = new JButton("Lowercase");
        p2Options.add(lc);
        lc.setForeground(Color.WHITE);
        lc.setBackground(Color.gray);
        p2Options.add(lc);

        JButton uc = new JButton("Uppercase");
        uc.setForeground(Color.WHITE);
        uc.setBackground(Color.gray);
        p2Options.add(uc);

        JButton digits = new JButton("Digits");
        digits.setForeground(Color.WHITE);
        digits.setBackground(Color.gray);
        p2Options.add(digits);

        JButton sc = new JButton("Special characters");
        sc.setForeground(Color.white);
        sc.setBackground(Color.gray);
        p2Options.add(sc);

        lc.addActionListener(e -> {
            setCharacterSetLowerCase(true);
            lc.setEnabled(false);
        });

        uc.addActionListener(e -> {
            setCharacterSetUpperCase(true);
            uc.setEnabled(false);
        });

        digits.addActionListener(e -> {
            setCharacterSetDigits(true);
            digits.setEnabled(false);
            Logger.log("GUI: DIGITS: " + isCharacterSetDigits());
        });

        sc.addActionListener(e -> {
            setCharacterSetSpecialCharacters(true);
            sc.setEnabled(false);
        });

        //--------FOURTH ROW-----------

        JPanel pAttackType = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pAttackType.setBackground(Color.DARK_GRAY);

        Label labelSelectAttackType = new Label("Select the attack type:");
        labelSelectAttackType.setForeground(Color.white);
        labelSelectAttackType.setFont(boldFont);
        pAttackType.add(labelSelectAttackType);

        JButton bfButton = new JButton("Brute Force Attack");
        pAttackType.add(bfButton);
        bfButton.setForeground(Color.WHITE);
        bfButton.setBackground(Color.gray);
        pAttackType.add(bfButton);

        JButton daButton = new JButton("Dictionary Attack");
        pAttackType.add(daButton);
        daButton.setForeground(Color.WHITE);
        daButton.setBackground(Color.gray);
        pAttackType.add(daButton);

        bfButton.addActionListener(e -> {
            setBfType(true);
            bfButton.setEnabled(false);
            daButton.setEnabled(false);
        });

        daButton.addActionListener(e -> {
            setDaType(true);
            daButton.setEnabled(false);
            daButton.setEnabled(false);
        });



        //--------FIFTH ROW-------------
        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p3.setBackground(Color.DARK_GRAY);

        Label labelInputMaxLength = new Label("Input the max length of the password:");
        labelInputMaxLength.setForeground(Color.white);
        labelInputMaxLength.setFont(boldFont);
        p3.add(labelInputMaxLength);

        TextField maxLength = new TextField();
        maxLength.setBackground(Color.WHITE);
        p3.add(maxLength);

        maxLength.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });

        JButton set = new JButton("Set max!");
        p3.add(set);

        set.addActionListener(e -> {
            maxLength.setEnabled(false);
            setMaxPasswordLength(Integer.parseInt(maxLength.getText()));
            //Logger.log("Max length is: " + getMaxPasswordLength());
        });


        //---------FIFTH ROW----------
        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p4.setBackground(Color.DARK_GRAY);

        Label labelInputPassword = new Label("Input the target password hash bellow:");
        labelInputPassword.setForeground(Color.white);
        labelInputPassword.setFont(boldFont);
        p4.add(labelInputPassword);

        //---------SIXTH ROW----------
        JPanel p4Pass = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p4Pass.setBackground(Color.DARK_GRAY);

        TextField inputPass = new TextField(35);
        inputPass.setBackground(Color.WHITE);

        JButton startButton = new JButton("Start cracking!");

        p4Pass.add(inputPass);
        p4Pass.add(startButton);


        //----------SEVENTH ROW----------------
        /*JPanel p5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p5.setBackground(Color.DARK_GRAY);
        JButton restartButton = new JButton("Restart!");
        restartButton.setBackground(Color.RED);
        restartButton.setForeground(Color.WHITE);

        p5.add(restartButton);

        restartButton.addActionListener(e -> {
            frame.dispose();
            //Helper newHelper = new Helper();
        });*/


        //---------EIGHTH ROW--------------------
        JPanel p6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p6.setBackground(Color.DARK_GRAY);
        crackBar.setStringPainted(true);
        crackBar.setValue(0);
        crackBar.setVisible(true);
        p6.add(crackBar);

        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            setStart(true);
            setPasswordInput(inputPass.getText());
        });

        //--------TENTH ROW--------------
        JPanel p7 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p7.setBackground(Color.DARK_GRAY);
        //password.setPreferredSize(new Dimension(50, 20));
        password.setForeground(Color.green);
        password.setFont(boldFont);
        p7.add(password);

        panel.add(p1);
        panel.add(p2);
        panel.add(p2Options);
        panel.add(pAttackType);
        panel.add(p3);
        panel.add(p4);
        panel.add(p4Pass);
        panel.add(p6);
        panel.add(p7);

        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void fill(double val) {
        crackBar.setString((Math.round(val*100.)/100.) +"%");
    }

    public void finalPassword (String pass){
        password.setText(pass);
    }

    public int getMaxPasswordLength() {
        return maxPasswordLength;
    }

    public void setMaxPasswordLength(int maxPasswordLength) {
        this.maxPasswordLength = maxPasswordLength;
    }

    public void setTargetSHA5(boolean targetSHA5) {
        this.targetSHA5 = targetSHA5;
    }

    public void setTargetMD5(boolean targetMD5) {
        this.targetMD5 = targetMD5;
    }

    public void setCharacterSetDigits(boolean characterSetDigits) {
        this.characterSetDigits = characterSetDigits;
    }

    public void setCharacterSetLowerCase(boolean characterSetLowerCase) {
        this.characterSetLowerCase = characterSetLowerCase;
    }

    public void setCharacterSetSpecialCharacters(boolean characterSetSpecialCharacters) {
        this.characterSetSpecialCharacters = characterSetSpecialCharacters;
    }

    public void setCharacterSetUpperCase(boolean characterSetUpperCase) {
        this.characterSetUpperCase = characterSetUpperCase;
    }

    public boolean isTargetMD5(){
        return targetMD5;
    }

    public boolean isTargetSHA256(){
        return targetSHA5;
    }

    public boolean isBfType() {
        return bfType;
    }

    public void setBfType(boolean bfType) {
        this.bfType = bfType;
    }

    public boolean isDaType() {
        return daType;
    }

    public void setDaType(boolean daType) {
        this.daType = daType;
    }

    public boolean isCharacterSetDigits(){
        return characterSetDigits;
    }

    public boolean isCharacterSetUpperCase(){
        return characterSetUpperCase;
    }

    public boolean isCharacterSetLowerCase() {
        return characterSetLowerCase;
    }

    public boolean isCharacterSetSpecialCharacters() {
        return characterSetSpecialCharacters;
    }

    public void setPasswordInput(String passwordInput) {
        this.passwordInput = passwordInput;
    }

    public String getPasswordInput() {
        return passwordInput;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
