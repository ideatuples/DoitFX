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

package net.tuples.doitfx.connector;

import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import net.tuples.doitfx.connector.config.AccountConfigManager;
import net.tuples.doitfx.connector.config.RecvPropsConverter;
import net.tuples.doitfx.connector.crypto.PasswordManager;
import net.tuples.doitfx.core.AppInitialiser;
import net.tuples.doitfx.core.MessageReceiver;
import net.tuples.doitfx.core.MessageSender;

/**
 * Connector class is a facade pattern class 
 * to establish connections easily by developers.
 * 
 * Primarily, we need to invoke when we use the library.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * @see MessageSender
 * @see MessageReceiver
 **/

public class Connector {
    
    private final AppInitialiser appInit;
    
    private final AccountConfigManager accConfigManager;
    private final SessionManager recvSessionManager;
    private final ConnectManager connectManager;
    private final PasswordManager pwManager;
    
    private final MessageReceiver msgReceiver;
    private final MessageSender msgSender;
    
    /**
     * The Constructor.
     * 
     * This is the constructor which initialise 'Manager' classes 
     * and make them ready to make connections.
     * 
     * No need to put parameters in.
     * 
     **/
    public Connector() {
        
        this.appInit = AppInitialiser.getInstance();
        
        this.accConfigManager = appInit.getAccConfigManager();
        this.recvSessionManager = appInit.getRecvSessionManager();
        this.connectManager = appInit.getConnectManager();
        this.pwManager = appInit.getPWManager();
        
        this.msgReceiver = new MessageReceiver(this.appInit);
        this.msgSender = new MessageSender(this.appInit);
        
    }
    
    private boolean newRecvConnection(final String pSvcPair, 
            final String pAccUUID, final Properties pActualProps) {
        
        final Session tgtSession;
        
        tgtSession = recvSessionManager
                        .generateNewSession(pSvcPair, pAccUUID, pActualProps);
                
        
        try {
            connectManager
                    .generateNewStore(pSvcPair, tgtSession)
                    .connect();
            
            return true;

        } catch (MessagingException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    /**
     * 
     * Closing specific Store object explicitly.
     * 
     * @param pSvcPair The parameter to distinguish individual account.
     * @return True or False
     */
    public boolean closeStore(final String pSvcPair) {
        
        final Store cachedStore;
        cachedStore = connectManager.deleteStore(pSvcPair);
        
        try {
            cachedStore.close();
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    /**
     * Closing all store objects. 
     * 
     * In other words, "Initialisation"
     * @return True or False
     */
    
    public boolean closeAllStores() {
        
        final Collection<Store> cachedStores;
        
        cachedStores = connectManager.getEntireStores();
        
        for (Store eachStore : cachedStores) {
            
            try {
                
                eachStore.close();
            
            } catch (MessagingException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return connectManager.clearStoreCache();
    }
    
    /**
     * One of the core methods, make connections through this method.
     * 
     * When you put pSvcPair value into the parameter you are ready to transfer 
     * messages.
     * 
     * @param pSvcPair The parameter to distinguish individual account.
     * @return True or False
     */
    public boolean connect(final String pSvcPair) {
        
        final String appUUID;
        final String accUUID;
        
        final Properties rawProps;
        final Properties actualProps;
        
        rawProps = this.accConfigManager.getRawRecvProps(pSvcPair);
        accUUID = rawProps.getProperty("unique_id");
        
        actualProps = RecvPropsConverter.getActualProps(rawProps);
        
        return newRecvConnection(pSvcPair, accUUID, actualProps);
    }
    
    /**
     * Getting MessageReceiver object to receive messages 
     * which is acting like remote controller.
     * 
     * @return MessageReceiver object.
     */
    public final MessageReceiver getMessageReceiver() {
        
        return msgReceiver;
    }
    
    /**
     * Getting MessageSender object to send messages 
     * which is acting like remote controller.
     * 
     * @return MessageSender object.
     */
    public final MessageSender getMessageSender() {
        
        return msgSender;
    }
        
}
