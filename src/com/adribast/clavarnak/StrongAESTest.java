package com.adribast.clavarnak;

import org.junit.Test;

import java.security.Key;

import static org.junit.Assert.*;

public class StrongAESTest {
    StrongAES test = new StrongAES();

    static byte[] texte ;

    @Test
    public void keyGen() {
        System.out.println(test.keyGen().toString());
    }

    @Test
    public void encrypt() {
        Key k = test.keyGen();
        String t = "j espere que ca marche et que ca crypte bien";
        texte = (test.encrypt(k,t));
    }

    @Test
    public void decrypt() {
        Key k = test.keyGen();

        System.out.println(texte);
        //String t = "j espere que ca marche et que ca crypte bien";
        //byte[] crypted = test.encrypt(k,t);
        System.out.println(test.decrypt(k,texte));
    }

}