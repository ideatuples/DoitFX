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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

/**
 * This class is to perform those tasks to generate new Stores and
 * cache Stores that was already established.
 * 
 * 
 * With ConnectManager, you can easily access Stores.
 * 
 * As you know, Stores are for receiving messages 
 * and Transports are sending messages.
 *
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * @see Store
 * @see Transport
 */

public class ConnectManager {
    
    
    private final Map<String, Store> storeCacheMap;
    private final Map<String, Transport> transportCacheMap;
    
     /**
     *
     * Constructor.
     * <br />
     * 
     * There is no need to put parameter to invoke this class.
     * ConnectManager is called by AppInitialiser which is a Singleton class.
     * 
     * @since version 0.0.1
     */
    
    public ConnectManager() {
        this.storeCacheMap = new HashMap<>();
        this.transportCacheMap = new HashMap<>();
        
        this.initialise();
    }
    
    private boolean initialise() {
        
        this.storeCacheMap.clear();
        this.transportCacheMap.clear();
        
        return true;
    }
    
    /**
     * Checking out already established Store.
     * @param pSvcPair The parameter to distinguish individual account.
     * @return True of False
     **/
    public final boolean checkExistingStore(final String pSvcPair) {
        
        if(!storeCacheMap.isEmpty() || storeCacheMap.containsKey(pSvcPair)) {
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Getting a Store object that was already set by pSvcPair.
     * 
     * @param pSvcPair The parameter for distinguish individual account.
     * @return A Store object that was associated with pSvcPair.
     **/
    
    public final Store getExistingStore(final String pSvcPair) {
        
        return storeCacheMap.get(pSvcPair);
    }
    
    /**
     * Generating new Store object.
     * 
     * @param pSvcPair The parameter for distinguish individual account.
     * @param pRecvSession To generate new Store object, we need to have a session of an account.
     * Store object is related with Receiving message action, so we put a session for receiving.
     * 
     * @return get an existing Store object from Store Cache.
     */
    
    public final Store generateNewStore(final String pSvcPair, 
            final Session pRecvSession) {
        
        final Store tgtStore;
        
        try {
            tgtStore = pRecvSession.getStore();
            storeCacheMap.put(pSvcPair, tgtStore);
            
            return tgtStore;
            
        
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ConnectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /*
    public final boolean checkExistingTransport(final String pSvcPair) {
        
        if(!transportCacheMap.isEmpty() || transportCacheMap.containsKey(pSvcPair)) {
            
            return true;
        }
        
        return false;
    }
    
    public final Transport getExistingTransport(final String pSvcPair) {
        
        return transportCacheMap.get(pSvcPair);
    }
        
    public Transport generateNewTransport(final String pSvcPair, 
            final Session pSendSession) {
        
        final Transport tgtTransport;
        
        try {
            tgtTransport = pSendSession.getTransport();
            transportCacheMap.put(pSvcPair, tgtTransport);
            
            return tgtTransport;
            
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ConnectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    */
    
    
    
    /*
    public final Store getStore(final String pSvcPair, 
            final Session pRecvSession) {
        
        Store tgtStore;
        
        tgtStore = checkExistingStore(pSvcPair);
        
        if (tgtStore == null) {
            
            tgtStore = generateNewStore(pSvcPair, pRecvSession);
            
        }
        
        return tgtStore;
    }
    
    public final Transport getTransport(final String pSvcPair, 
            final Session pSendSession) {
        
        Transport tgtTransport;
        
        tgtTransport = checkExistingTransport(pSvcPair);
        
        if (tgtTransport == null) {
            
            tgtTransport = generateNewTransport(pSvcPair, pSendSession);
        }
        
        return tgtTransport;
    }
    */
    
    /*
    public Collection<Transport> getEntireTransports() {
        
        return transportCacheMap.values();
    }
    */
    
    /**
     * Getting all stores that has been cached.
     * 
     * @return A collection object of Stores.
     */
    public Collection<Store> getEntireStores() {
        
        return storeCacheMap.values();
    }
    
    /**
     * Deletion of specific Store object from cache.
     * 
     * @param pSvcPair The parameter for distinguish individual account.
     * @return The Store object we demanded, but deleted from cache.
     */
    public final Store deleteStore(final String pSvcPair) {
        
        return storeCacheMap.remove(pSvcPair);
    }
    
    /**
     * Clear all Store object that has been cached. Simply, making it empty.
     * 
     * @return True or False
     */
    public final boolean clearStoreCache() {
        
        storeCacheMap.clear();
        
        return storeCacheMap.isEmpty();
    }
    
}
