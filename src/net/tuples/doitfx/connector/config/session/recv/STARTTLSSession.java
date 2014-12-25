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
public final class STARTTLSSession extends SecuritySession {

    public STARTTLSSession(final String pProtocolType, final String pHostname, final int pPortNumber) {
        
        super(pProtocolType, pHostname, pPortNumber);
        generateSecurityProperties();
    }

    
    private void generateSecurityProperties() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /*
        StringChunk starttlsPortProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, super.protocolType.concat("s"))
                .mewingCat(StringChunk.dot, "port");
        */
        
        StringChunk starttlsEnableProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, super.protocolType.concat("s"))
                .mewingCat(StringChunk.dot, "starttls")
                .mewingCat(StringChunk.dot, "enable");
        
        StringChunk starttlsRequiredProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, super.protocolType.concat("s"))
                .mewingCat(StringChunk.dot, "starttls")
                .mewingCat(StringChunk.dot, "required");
        
        //super.securityProperties.setProperty(starttlsPortProperty.toString(), String.valueOf(super.portNumber));
        super.securityProperties.setProperty(starttlsEnableProperty.toString(), "true");
        super.securityProperties.setProperty(starttlsRequiredProperty.toString(), "true");
        
    
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
