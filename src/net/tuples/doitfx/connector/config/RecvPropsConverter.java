/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.config;

import java.util.Properties;
import net.tuples.doitfx.connector.config.session.recv.ISecuritySession;
import net.tuples.doitfx.connector.config.session.recv.SecuritySessionFactory;

/**
 *
 * @author ideatuples
 */
public class RecvPropsConverter {
    
    private RecvPropsConverter() {
        
    }
    
    public static Properties getActualProps(final Properties pTgtProperties) {
        
        final ISecuritySession sessionConfig;
        
        final String protocolType;
        final String hostname;
        final int portnumber;
        final String encryptionType;
        
        protocolType = pTgtProperties.getProperty("protocol");
        hostname = pTgtProperties.getProperty("host");
        portnumber = Integer.parseInt(pTgtProperties.getProperty("port"));
        encryptionType = pTgtProperties.getProperty("encryption");
        
        sessionConfig = SecuritySessionFactory
                .getSecurityFeature(protocolType, hostname, portnumber, encryptionType);
        
        return sessionConfig.getSecurityProperties();
    }
    
}
