/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author ideatuples
 */
public class Connector {
    
    private final AppInitialiser appInit;
    
    private final AccountConfigManager accConfigManager;
    private final SessionManager recvSessionManager;
    private final ConnectManager connectManager;
    private final PasswordManager pwManager;
    
    private final MessageReceiver msgReceiver;
    private final MessageSender msgSender;
    
    
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
    
    public final MessageReceiver getMessageReceiver() {
        
        return msgReceiver;
    }
    
    public final MessageSender getMessageSender() {
        
        return msgSender;
    }
        
}
