/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author ideatuples
 */
public class ConnectManager {
    
    private final Map<String, Store> storeCacheMap;
    private final Map<String, Transport> transportCacheMap;
    
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
    
    public final boolean checkExistingStore(final String pSvcPair) {
        
        if(!storeCacheMap.isEmpty() || storeCacheMap.containsKey(pSvcPair)) {
            
            return true;
        }
        
        return false;
    }
    
    public final Store getExistingStore(final String pSvcPair) {
        
        return storeCacheMap.get(pSvcPair);
    }
    
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
    
    public Collection<Store> getEntireStores() {
        
        return storeCacheMap.values();
    }
    
    public final Store deleteStore(final String pSvcPair) {
        
        return storeCacheMap.remove(pSvcPair);
    }
    
    public final boolean clearStoreCache() {
        
        storeCacheMap.clear();
        
        return storeCacheMap.isEmpty();
    }
    
}
