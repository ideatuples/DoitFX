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

package net.tuples.doitfx.core;

import net.tuples.doitfx.connector.ConnectManager;
import net.tuples.doitfx.connector.SessionManager;
import net.tuples.doitfx.connector.config.AccountConfigManager;
import net.tuples.doitfx.connector.crypto.PasswordManager;
import net.tuples.doitfx.core.config.AppConfigManager;

/**
 * AppInitialiser is the initialiser of DoitFX.
 * 
 * AppInitialiser controls every manager.
 * This is a kind of implementation of the Singleton design pattern.
 * 
 * It also has some methods to return each manager to the point of the calling.
 * 
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 **/

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

    /**
     * For getting AccountConfigManager class.
     * 
     * To load the raw configuration Properties classes, you need to invoke
     * this method first and find whatever you in configuration files.
     * 
     * @return AccountConfigManager
     */
    public final AccountConfigManager getAccConfigManager() {
        return accConfigManager;
    }

    /**
     * For getting ConnectManager class.
     * 
     * If you go to get Store objects which hold something to receive e-mails 
     * from servers, call this method and take it.
     * 
     * @return ConnectManager
     */
    public final ConnectManager getConnectManager() {
        return connectManager;
    }
    
    
    /**
     * For getting PasswordManager class.
     * 
     * If you go to get Store objects which hold something to receive e-mails 
     * from servers, call this method and take it.
     * 
     * @return PasswordManager
     */
    public final PasswordManager getPWManager() {
        return pwManager;
    }

    /**
     * For getting SessionManager class for receiving messages.
     * 
     * @return SessionManager
     */
    public final SessionManager getRecvSessionManager() {
        return recvSessionManager;
    }

    /**
     * For getting SessionManager class for sending messages.
     * 
     * @return SessionManager
     */
    public final SessionManager getSendSessionManager() {
        return sendSessionManager;
    }
    
    /**
     * getInstance is a famous type of methods.
     * 
     * You can get AppInitialiser at any time, at any where.
     * 
     * @return AppIntialiser itself. 
     */
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
