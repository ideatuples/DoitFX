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

package net.tuples.doitfx.connector.config;

import java.util.Properties;
import net.tuples.doitfx.connector.config.session.recv.ISecuritySession;
import net.tuples.doitfx.connector.config.session.recv.SecuritySessionFactory;

/**
 * RecvProsConverter class is to convert the configurations for RECEIVING
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

public class RecvPropsConverter {
    
    private RecvPropsConverter() {
        
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
