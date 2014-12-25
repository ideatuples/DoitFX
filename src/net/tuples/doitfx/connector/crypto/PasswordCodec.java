/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.crypto;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author ideatuples
 */
public class PasswordCodec {
    
    private final byte[] initVector;
    private final AlgorithmParameterSpec algParamSpec;
    
    public PasswordCodec() {
        
        this.initVector = new byte[] { 0x10, 0x10, 0x01, 0x04, 0x01, 0x01, 0x01, 0x02 };
        this.algParamSpec = new IvParameterSpec(initVector);
        
    }
    
    public final byte[] activateEncryption(final Key pTheSecretKey, final byte[] pPlainBytes) {
        
        final Cipher encrypter;
        final byte[] plainBytes;
        final byte[] encryptedBytes;
        
        try {
            
            encrypter = Cipher.getInstance("DES/CBC/PKCS5Padding");
            encrypter.init(Cipher.ENCRYPT_MODE, pTheSecretKey, algParamSpec);
            //encrypter.init(Cipher.ENCRYPT_MODE, pTheSecretKey);
            
            encryptedBytes = encrypter.doFinal(pPlainBytes);
            
            return encryptedBytes;
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException ex) {
            /**
             *   This is for the exceptions from the Cipher Object.
             **/
            Logger.getLogger(PasswordCodec.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            /**
             *  This is for the exceptions from the Charset Object.
             **/
            Logger.getLogger(PasswordCodec.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return null;
    }
    
    public final byte[] activateDecryption(final Key pTheSecretKey, final byte[] pCipherBytes) {
        
        final Cipher decrypter;
        final byte[] decryptedBytes;
        
        try {
            decrypter = Cipher.getInstance("DES/CBC/NoPadding");
            decrypter.init(Cipher.DECRYPT_MODE, pTheSecretKey, algParamSpec);
            //decrypter.init(Cipher.DECRYPT_MODE, pTheSecretKey);
            
            decryptedBytes = decrypter.doFinal(pCipherBytes);
            
            return decryptedBytes;
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException ex) {
            /**
             *   This is for the exceptions from the Cipher Object.
             **/
            Logger.getLogger(PasswordCodec.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(PasswordCodec.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
}

