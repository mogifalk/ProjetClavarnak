package com.adribast.clavarnak;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StrongAESTest {
    StrongAES test = new StrongAES();

    static byte[] texte ;

    public StrongAESTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
    }

    @Test
    public void keyGen() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(test.keyGen().toString());
    }

    @Test
    public void encrypt() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Key k = test.keyGen();
        String t = "Mogiflak";
        texte = (test.encrypt(k,t));
    }

    @Test
    public void decrypt() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Key k = test.keyGen();

        System.out.println(Arrays.toString(texte));
        //String t = "j espere que ca marche et que ca crypte bien";
        //byte[] crypted = test.encrypt(k,t);
        System.out.println(test.decrypt(k,texte));
    }

    @Test
    public void hash() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(Arrays.toString(test.hash()));
    }
}