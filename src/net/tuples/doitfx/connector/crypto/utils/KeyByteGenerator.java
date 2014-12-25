/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.crypto.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author ideatuples
 */
public class KeyByteGenerator {
    
    private static KeyGenerator keyGen;
    
    private KeyByteGenerator() {
        
    }
    
    public static final SecretKey activateKeyGeneration() {
        
        final SecretKey theKey;
        final SecureRandom theRandom = RandomGenerator.getTheRandom();
        
        
        try {
            keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56, theRandom);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyByteGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        theKey = keyGen.generateKey();
        
        return theKey;
    }
    
    static class RandomGenerator {
        
        public static final SecureRandom getTheRandom() {
            
            final SecureRandom rNumberGenerator;
            
            
            try {
                rNumberGenerator = SecureRandom.getInstance("SHA1PRNG");
                return rNumberGenerator;
                
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(KeyByteGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            return null;
           
        }
        
        
    }
    
}
