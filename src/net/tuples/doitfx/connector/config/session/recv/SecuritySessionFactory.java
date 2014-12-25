/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.config.session.recv;

/**
 *
 * @author ideatuples
 */
public class SecuritySessionFactory {
    
    private SecuritySessionFactory() {
        
    }
    /*
    public final static ISecuritySession getSecurityFeature(final String pProtocolType, final String pHostname, final int pPortNumber, final String pEncryptionType) {
        
        switch(pEncryptionType) {
            case "SSL":
                return new SSLSession(pProtocolType, pHostname, pPortNumber);
                
            case "STARTTLS":
                return new STARTTLSSession(pProtocolType, pHostname, pPortNumber);
                
            case "NOSEC":
                return new NOSecuritySession(pProtocolType, pHostname, pPortNumber);
                
        }
        return null;
    }
    */
    public final static ISecuritySession getSecurityFeature(final String pProtocolType, final String pHostname, final int pPortNumber, final String pEncryptionType) {
        
        switch(pEncryptionType) {
            case "SSL":
                return new SSLSession(pProtocolType, pHostname, pPortNumber);
                
                
            case "STARTTLS":
                return new STARTTLSSession(pProtocolType, pHostname, pPortNumber);
                
            case "NOSEC":
                return new NOSecuritySession(pProtocolType, pHostname, pPortNumber);
                
        }
        return null;
    }
    
    
    
    
}
