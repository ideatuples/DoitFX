/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author ideatuples
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
    
    public final boolean checkExistingSession(final String pSvcPair) {
        
        if(sessionCacheMap.isEmpty() || !sessionCacheMap.containsKey(pSvcPair)) {
            return false;
        } 
        
        return true;
    }
    
    public final Session getExistingSession(final String pSvcPair) {
        
        return sessionCacheMap.get(pSvcPair);
    }
    
    public final Session generateNewSession(final String pSvcPair,
            final String pAccUUID, final Properties pActualProps) {
        
        final PasswordManager pwManager = AppInitialiser.getInstance().getPWManager();
        final PWAuthenticator pwAuth;
        final Session newSession;
        
        final String username;
        final String password;
        
        
        
        
        username = pSvcPair.split("\\/")[1];
        password = pwManager.getAccountKey(pSvcPair, pAccUUID).trim();
        
        pwAuth = new PWAuthenticator(username, password);
        //pwAuth = new PWAuthenticator(username, password);
        newSession = Session.getInstance(pActualProps, pwAuth);
        
        sessionCacheMap.put(pSvcPair, newSession);
        
        return newSession;
    }
    
    
    
}
