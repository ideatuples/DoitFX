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

package net.tuples.doitfx.connector.crypto.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * KeyByteGenerator is the generator keys to be stored in KeyStore object.
 * 
 * KeyStore stores keys that was randomly generated.
 * 
 * Also, users can't select any words or codes to use as password.
 * 
 * Therefore, developers use this class to generate passwords randomly.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 **/
public class KeyByteGenerator {
    
    private static KeyGenerator keyGen;
    
    private KeyByteGenerator() {
        
    }
    
    /**
     * Generate a key to be stored in KeyStore.
     * 
     * @return SecretKey type constant.
     **/
    
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
    
    /**
     * This inner class is for the generation of random number 
     * to make key to be used as key.
     */
    static class RandomGenerator {
        
        /**
         * 
         * Generating and returning a random number.
         * 
         * @return SecureRandom type constant.
         */
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
