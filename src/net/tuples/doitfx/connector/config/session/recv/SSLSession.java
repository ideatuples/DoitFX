/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.config.session.recv;

import java.util.Properties;
import net.tuples.doitfx.core.utils.StringChunk;
/**
 *
 * @author ideatuples
 */
public final class SSLSession extends SecuritySession {

    public SSLSession(final String pProtocolType, final String pHostname, final int pPortNumber) {
        
        super(pProtocolType, pHostname, pPortNumber);
        generateSecurityProperties();
        
    }
   
    private void generateSecurityProperties() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        StringChunk sslEnableProerty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, super.protocolType.concat("s"))
                .mewingCat(StringChunk.dot, "ssl")
                .mewingCat(StringChunk.dot, "enable");
        
        super.securityProperties.setProperty(sslEnableProerty.toString(), "true");
        
    }

    @Override
    public Properties getSecurityProperties() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        if(super.securityProperties.isEmpty()) {
            
            return null;
        }
        
        return super.securityProperties;
    
    }

    

}