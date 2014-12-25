/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.crypto.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ideatuples
 */
public class DigestGenerator {
    private final MessageDigest theDigestGen;
    
    public DigestGenerator(final String pDigestAlgorithm) {
        theDigestGen = internalInitialiser(pDigestAlgorithm);
        
    }
    
    private MessageDigest internalInitialiser(final String pDigestAlgorithm) {
        
        final MessageDigest tempGen;
        
        try {
            tempGen = MessageDigest.getInstance(pDigestAlgorithm);
            return tempGen;
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigestGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public final byte[] getIDDigest(final String pTargetString) {
        
        final byte[] targetBytes;
        final byte[] digestedBytes;
        
        try {
            targetBytes = pTargetString.getBytes("ASCII");
            digestedBytes = theDigestGen.digest(targetBytes);
            
            return digestedBytes;
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DigestGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
