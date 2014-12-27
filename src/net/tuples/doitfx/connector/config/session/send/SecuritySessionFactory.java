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

package net.tuples.doitfx.connector.config.session.send;

/**
 * SecuritySessionFactory is an factory method type class to invoke each encryption 
 * session class following the name that developers calls.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 **/
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
    
    /**
     * This static method returns right *Session class following the encryption method.
     * 
     * @param pProtocolType E-Mail protocol type.
     * @param pHostname The host name of the server.
     * @param pPortNumber the port number of connections to the server.
     * @param pEncryptionType The encryption method which will be used by connections.
     * @return 
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
