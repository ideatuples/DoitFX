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
 * SecuritySession is an abstract class to make inherit to sub classes.
 * 
 * This class is implemented ISecuritySession.
 * Also, the declared method in ISecuritySession inherits to sub classes 
 * in the way of declaration of abstract method.
 * 
 * This makes delay to invoke until the sub classes is actually invoked.
 * 
 * The purpose of this class is to set default behaviour 
 * that is shared by the sub classes.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * 
 **/
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
        
        StringChunk store_ProtocolProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, "store")
                .mewingCat(StringChunk.dot, "protocol");
        /*
        StringChunk transport_ProtocolProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, "transport")
                .mewingCat(StringChunk.dot, "protocol");
        */
        
        StringChunk hostnameProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, protocolType.concat("s"))
                .mewingCat(StringChunk.dot, "host");
        
        StringChunk portProperty = new StringChunk("mail")
                .mewingCat(StringChunk.dot, protocolType.concat("s"))
                .mewingCat(StringChunk.dot, "port");
        
        securityProperties.setProperty(store_ProtocolProperty.toString(), protocolType.concat("s"));
        //securityProperties.setProperty(transport_ProtocolProperty.toString(), protocolType.concat("s"));
        securityProperties.setProperty(hostnameProperty.toString(), hostname);
        securityProperties.setProperty(portProperty.toString(), String.valueOf(portNumber));
        
        return true;
    }
    
    @Override
    public abstract Properties getSecurityProperties();
    
}
