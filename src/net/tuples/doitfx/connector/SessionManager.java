/*
 * Copyright (C) 2014 ideatuples
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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Session;
import net.tuples.doitfx.connector.auth.PWAuthenticator;
import net.tuples.doitfx.connector.crypto.PasswordManager;
import net.tuples.doitfx.core.AppInitialiser;

/**
 * This class is to manager Session objects.
 * 
 * SessionManager hold information about account configurations and
 * decrypt passwords which was encrypted for servers and services.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * @see Store
 * @see Transport
 */

public class SessionManager {
    
    private final Map<String, Session> sessionCacheMap;
    
    
    public SessionManager() {
        
        this.sessionCacheMap = new HashMap<>();
        
        this.initialise();
        
    }
    
    private boolean initialise() {
        
        sessionCacheMap.clear();
        
        return true;
    }
    
    /*
    public final Map<String, Session> getCacheMap() {
        
        return sessionCacheMap;
    }
    */
    
    /**
     * Checking a session which is already existed.
     * @param pSvcPair The parameter to distinguish individual account.
     * @return True or False
     */
    public final boolean checkExistingSession(final String pSvcPair) {
        
        if(sessionCacheMap.isEmpty() || !sessionCacheMap.containsKey(pSvcPair)) {
            return false;
        } 
        
        return true;
    }
    
    /**
     * Getting a Session object that was already set by pSvcPair.
     * 
     * @param pSvcPair The parameter for distinguish individual account.
     * @return A Store object that was associated with pSvcPair.
     **/
    public final Session getExistingSession(final String pSvcPair) {
        
        return sessionCacheMap.get(pSvcPair);
    }
    
    /**
     * Generating new Session object.
     * 
     * @param pSvcPair The parameter for distinguish individual account.
     * @param pAccUUID The unique id for individual account.
     * @param pActualProps The actual properties for the account.
     * 
     * @return Newly generated session.
     **/
    
    public final Session generateNewSession(final String pSvcPair,
            final String pAccUUID, final Properties pActualProps) {
        
        final PasswordManager pwManager = AppInitialiser.getInstance().getPWManager();
        final PWAuthenticator pwAuth;
        final Session newSession;
        
        final String username;
        final String password;
        
        username = pSvcPair.split("\\/")[1];
        password = pwManager.decryptText(pSvcPair, pAccUUID).trim();
        
        pwAuth = new PWAuthenticator(username, password);
        //pwAuth = new PWAuthenticator(username, password);
        newSession = Session.getInstance(pActualProps, pwAuth);
        
        sessionCacheMap.put(pSvcPair, newSession);
        
        return newSession;
    }
    
    
    
}
