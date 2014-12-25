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
public abstract class SecuritySession implements ISecuritySession {
    
    protected final Properties securityProperties;
    protected final String protocolType;
    protected final String hostname;
    protected final int portNumber;
    
    protected SecuritySession(final String pProtocolType, final String pHostname, final int pPortNumber) {
        
        this.securityProperties = new Properties();
        this.protocolType = pProtocolType;
        this.hostname = pHostname;
        this.portNumber = pPortNumber;
        
        this.initSecurityProperties();
    }

    private boolean initSecurityProperties() {
        /*
        StringChunk store_ProtocolProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, "store")
                .mewingCat(StringChunk.dot, "protocol");
        */
        StringChunk transport_ProtocolProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, "transport")
                .mewingCat(StringChunk.dot, "protocol");
        
        StringChunk auth_Property = new StringChunk("mail")
                .mewingCat(StringChunk.dot, protocolType)
                .mewingCat(StringChunk.dot, "auth");
        
        
        StringChunk hostnameProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, protocolType)
                .mewingCat(StringChunk.dot, "host");
        
        StringChunk portProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, protocolType)
                .mewingCat(StringChunk.dot, "port");
        
        //securityProperties.setProperty(store_ProtocolProperty.toString(), protocolType);
        securityProperties.setProperty(auth_Property.toString(), "true");
        securityProperties.setProperty(transport_ProtocolProperty.toString(), protocolType);
        securityProperties.setProperty(hostnameProperty.toString(), hostname);
        securityProperties.setProperty(portProperty.toString(), String.valueOf(portNumber));
        
        return true;
    }
    
    @Override
    public abstract Properties getSecurityProperties();
    
}
