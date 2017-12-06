package com.adribast.clavarnak;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
    public class StrongAES
    {
                String text = "Hello World";
                String key = "Jecrisseizecarac"; // 128 bit key
                public void keyGen (String key) {
                    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
                    Cipher cipher = null;
                    try {
                        cipher = Cipher.getInstance("AES");
                    } catch (NoSuchAlgorithmException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchPaddingException e1) {
                        e1.printStackTrace();
                    }
                }

                public void encrypt(Cipher cipher , Key aesKey) {
                    try {
                        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                    } catch (InvalidKeyException e1) {
                        e1.printStackTrace();
                    }
                    byte[] encrypted = new byte[0];
                    try {
                        encrypted = cipher.doFinal(text.getBytes());
                    } catch (IllegalBlockSizeException e1) {
                        e1.printStackTrace();
                    } catch (BadPaddingException e1) {
                        e1.printStackTrace();
                    }
                    System.err.println(new String(encrypted));
                }


                private String decrypt(Cipher cipher, Key aesKey, byte[] encrypted) {
                    try {
                        cipher.init(Cipher.DECRYPT_MODE, aesKey);
                    } catch (InvalidKeyException e1) {
                        e1.printStackTrace();
                    }
                    String decrypted = null;
                    try {
                        decrypted = new String(cipher.doFinal(encrypted));
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    }
                    System.err.println(decrypted);
                    return decrypted ;
                }
}
