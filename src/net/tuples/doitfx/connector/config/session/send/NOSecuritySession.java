/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.config.session.send;

import java.util.Properties;
import net.tuples.doitfx.core.utils.StringChunk;

/**
 *
 * @author ideatuples
 */
public class NOSecuritySession implements ISecuritySession {

    private final Properties noSecurityProperties;
    
    private final String protocolType;
    private final String hostname;
    private final int portNumber;
    
    public NOSecuritySession(final String pProtocolType, final String pHostname, final int pPortNumber) {
        
        this.noSecurityProperties = new Properties();
        
        this.protocolType = pProtocolType;
        this.hostname = pHostname;
        this.portNumber = pPortNumber;
        
    }

    private void generateSecurityProperties() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        StringChunk store_ProtocolProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, "store")
                .mewingCat(StringChunk.dot, "protocol");
        
        StringChunk transport_ProtocolProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, "transport")
                .mewingCat(StringChunk.dot, "protocol");
        
        StringChunk portProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, protocolType)
                .mewingCat(StringChunk.dot, "port");
        
        noSecurityProperties.setProperty(store_ProtocolProperty.toString(), protocolType);
        noSecurityProperties.setProperty(transport_ProtocolProperty.toString(), protocolType);
        noSecurityProperties.setProperty(portProperty.toString(), String.valueOf(portNumber));
        
       
    }

    @Override
    public final Properties getSecurityProperties() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(noSecurityProperties.isEmpty()) {
            
            return null;
        }
    
        return noSecurityProperties;
    }
    
    
}
