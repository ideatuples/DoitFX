/*
 * Copyright (C) 2014 Geuntaek Lee
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
 * PasswordCodec is a codec class that was designed for 
 * simplifying En/Decryption procedure.
 * 
 * This class is using Simple 'DES' algorithm to reduce En/Decryption time.
 * 
 * Initial Vector for En/Decryption is applied, 
 * but it was not mentioned on official Java API document.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 **/

public class PasswordCodec {
    
    private final byte[] initVector;
    private final AlgorithmParameterSpec algParamSpec;
    
    /**
     * The constructor of PasswordCodec.
     * 
     * No parameter is required.
     * 
     */
    public PasswordCodec() {
        
        this.initVector = new byte[] { 0x10, 0x10, 0x01, 0x04, 0x01, 0x01, 0x01, 0x02 };
        this.algParamSpec = new IvParameterSpec(initVector);
        
    }
    
    /**
     * Plaintext encryption method.
     * 
     * If you fill the 2 parameters, you will get encrypted byte array.
     * 
     * @param pTheSecretKey Randomly generated Key by KeyByteGenerator.
     * @param pPlainBytes Converted plaintext to byte array.
     * @return Encrypted byte array will be returned.
     */
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
    
    /**
     * Ciphertext decryption method
     * 
     * @param pTheSecretKey The key that has stored in the KeyStore object.
     * @param pCipherBytes The byte array type ciphertext to be decrypted.
     * @return Decrypted byte array will be returned.
     */
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

