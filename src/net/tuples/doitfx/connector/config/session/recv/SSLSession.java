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

package net.tuples.doitfx.connector.config.session.recv;

import java.util.Properties;
import net.tuples.doitfx.core.utils.StringChunk;


/**
 * SSLSession is a part of generating actual Properties object 
 * which is SSL/TLS encryption specific.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * 
 **/
public final class SSLSession extends SecuritySession {

    /**
     * The constructor of the class.
     * This constructor has 3 parameters, 
     * Host name, Port number and E-Mail protocol type.
     * 
     * @param pProtocolType E-Mail protocol type (IMAP or POP3).
     * @param pHostname The host name of the server.
     * @param pPortNumber The port number of the server.
     */
    
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
    
    /**
     * Getting generated actual Properties object.
     * 
     * @return Generated actual Properties object.
     */
    @Override
    public Properties getSecurityProperties() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        if(super.securityProperties.isEmpty()) {
            
            return null;
        }
        
        return super.securityProperties;
    
    }

    

}