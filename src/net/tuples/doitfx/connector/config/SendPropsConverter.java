/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.config;

import java.util.Properties;
import net.tuples.doitfx.connector.config.session.send.ISecuritySession;
import net.tuples.doitfx.connector.config.session.send.SecuritySessionFactory;


/**
 * SendProsConverter class is to convert the configurations for SENDING
 * which was stored into actual Properties for JavaMail.
 * 
 * JavaMail has its own Properties and it is not compatible 
 * with the configurations we stored. 
 * 
 * Therefore, we need to convert it into actual format Properties object.
 * 
 * Of course, it has only one method which is declared to static.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * @see ISecuritySession
 * @see SecuritySessionFactory
 * 
 **/

public class SendPropsConverter {
    
    private SendPropsConverter() {
        
    }
    
    /**
     * Put a Properties object that you want to connect as a parameter.
     * 
     * @param pTgtProperties Properties object that you want to connect
     * @return Converted Properties object.
     **/
    
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
