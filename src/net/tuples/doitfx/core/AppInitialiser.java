/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.core;

import net.tuples.doitfx.connector.ConnectManager;
import net.tuples.doitfx.connector.SessionManager;
import net.tuples.doitfx.connector.config.AccountConfigManager;
import net.tuples.doitfx.connector.crypto.PasswordManager;
import net.tuples.doitfx.core.config.AppConfigManager;

/**
 *
 * @author ideatuples
 */
public class AppInitialiser {

    private static AppInitialiser appInitialiser;
    
    private static AppConfigManager appConfigManager;
    private static String appUUID;
    
    private final AccountConfigManager accConfigManager;
    
    private ConnectManager connectManager;
    private PasswordManager pwManager;
    
    private SessionManager recvSessionManager;
    private SessionManager sendSessionManager;
    
    
    private AppInitialiser(final String pAppUUID) {
      
        
        this.accConfigManager = new AccountConfigManager();
        
        this.initialise();
        
    }
    
    private boolean initialise() {
        
        recvSessionManager = new SessionManager();
        sendSessionManager = new SessionManager();
        connectManager = new ConnectManager();
        pwManager = new PasswordManager(appUUID);
        
        return true;
    }

    public final AccountConfigManager getAccConfigManager() {
        return accConfigManager;
    }

    public final ConnectManager getConnectManager() {
        return connectManager;
    }

    public final PasswordManager getPWManager() {
        return pwManager;
    }

    
    public final SessionManager getRecvSessionManager() {
        return recvSessionManager;
    }

    public final SessionManager getSendSessionManager() {
        return sendSessionManager;
    }
    
    public static AppInitialiser getInstance() {
        
        if(appConfigManager == null) {
            appConfigManager = new AppConfigManager();
        }
        
        appUUID = appConfigManager.getPropertyValue("unique_id");
        
        if(appInitialiser == null) {
            
            appInitialiser = new AppInitialiser(appUUID);
        }
        
        return appInitialiser;
        
    }
}
