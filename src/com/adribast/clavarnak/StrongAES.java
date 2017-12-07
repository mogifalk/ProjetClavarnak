package com.adribast.clavarnak;
import java.io.UnsupportedEncodingException;
import java.security.*;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
    public class StrongAES {

        private String maChaine = "Mogifalk"+"uneoudeuxcouilles";

        public StrongAES() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        }

        public Key keyGen() throws UnsupportedEncodingException, NoSuchAlgorithmException {
            return new SecretKeySpec(this.hash(), "AES");

        }

        public byte[] encrypt(Key aesKey, String text) {

            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("AES");
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            } catch (NoSuchPaddingException e1) {
                e1.printStackTrace();
            }

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
            return encrypted;
        }


        public String decrypt(Key aesKey, byte[] encrypted) {
            Cipher cipher = null;

            try {
                cipher = Cipher.getInstance("AES");
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            } catch (NoSuchPaddingException e1) {
                e1.printStackTrace();
            }

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
            return decrypted;
        }

        public byte[] hash() throws NoSuchAlgorithmException, UnsupportedEncodingException {

            byte[] byteChaine = maChaine.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(byteChaine);

        }
    }
