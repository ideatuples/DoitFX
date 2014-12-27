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

import java.util.Properties;
import net.tuples.doitfx.core.utils.StringChunk;

/**
 * NOSecuritySession is a part of generating actual Properties object
 * with no security feature.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * 
 **/
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

    /**
     * Getting generated actual Properties object.
     * 
     * @return Generated actual Properties object.
     */
    @Override
    public final Properties getSecurityProperties() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(noSecurityProperties.isEmpty()) {
            
            return null;
        }
    
        return noSecurityProperties;
    }
    
    
}
