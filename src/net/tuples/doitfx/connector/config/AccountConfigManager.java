/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import net.tuples.doitfx.connector.config.utils.PathOptions;


/**
 *
 * @author ideatuples
 */
public class AccountConfigManager {
    
    
    private final AccountConfigLoader accConfigLoader;
    private final AccountConfigSaver accConfigSaver;
    
    private Map<String, Properties> rawRecvMap;
    private Map<String, Properties> rawSendMap;

    public AccountConfigManager() {
        
        this.accConfigLoader = new AccountConfigLoader();
        this.accConfigSaver = new AccountConfigSaver();
                
        this.initialise();
    }
    
    private boolean initialise() {
        
        rawRecvMap = accConfigLoader.getRecvMap();
        rawSendMap = accConfigLoader.getSendMap();
        
        return true;
    }
    
    private boolean replaceOneProps(final String pSvcPair,
            final PathOptions.Direction pDirection, final Properties pTgtProps) {
        
        switch(pDirection) {
            case RECV:
                rawRecvMap.put(pSvcPair, pTgtProps);
                
            case SEND:
                rawSendMap.put(pSvcPair, pTgtProps);
        }
        
        return true;
    }
    
    public final boolean setNewProps(final String pSvcPair,
            final PathOptions.Direction pDirection, final Properties pTgtProps) {
        
        accConfigSaver.setProps(pSvcPair, pDirection, pTgtProps);
        replaceOneProps(pSvcPair, pDirection, pTgtProps);
        
        return true;
    }
    
    public final boolean setEmptyRecvProps(final String pSvcPair) {
        
        final Properties newEmptyProps = new Properties();
        final PathOptions.Direction transferDirection = PathOptions.Direction.RECV;
        
        String accUUID = UUID.randomUUID().toString();
        
        newEmptyProps.setProperty("unique_id", accUUID);
        newEmptyProps.setProperty("encryption", "DEFAULT");
        newEmptyProps.setProperty("protocol", "DEFAULT");
        newEmptyProps.setProperty("host", "DEFAULT");
        newEmptyProps.setProperty("port", "65536");
        
        accConfigSaver.setProps(pSvcPair, transferDirection, newEmptyProps);
        replaceOneProps(pSvcPair, transferDirection, newEmptyProps);
        
        return true;
    }
    
    public final boolean setEmptySendProps(final String pSvcPair) {
        
        final Properties newEmptyProps = new Properties();
        final PathOptions.Direction transferDirection = PathOptions.Direction.SEND;
        final String accUUID;
        
        if(!rawRecvMap.containsKey(pSvcPair)) {
            
            final StringBuilder msgBuilder = 
                    new StringBuilder("There is no receive configuration which name is")
                            .append(" ")
                            .append(pSvcPair);
            
            throw new NullPointerException(msgBuilder.toString());
        }
        
        accUUID = rawRecvMap.get(pSvcPair).getProperty("unique_id");
        
        newEmptyProps.setProperty("unique_id", accUUID);
        newEmptyProps.setProperty("encryption", "DEFAULT");
        newEmptyProps.setProperty("protocol", "DEFAULT");
        newEmptyProps.setProperty("host", "DEFAULT");
        newEmptyProps.setProperty("port", "65536");
        
        accConfigSaver.setProps(pSvcPair, transferDirection, newEmptyProps);
        replaceOneProps(pSvcPair, transferDirection, newEmptyProps);
        
        return true;
    }
    
    public final Properties getRawRecvProps(final String pSvcPair) {
        
        final Properties theRawRecvProps;
        theRawRecvProps = rawRecvMap.get(pSvcPair);
        
        return theRawRecvProps;
    }
    
    public final Properties getRawSendProps(final String pSvcPair) {
        
        final Properties theRawSendProps;
        theRawSendProps = rawSendMap.get(pSvcPair);
        
        return theRawSendProps;
    }
        
    public final List<String> getAllSendSvcPair() {
        
        final List<String> svcPairList = new ArrayList<>();
        svcPairList.addAll(rawSendMap.keySet());
        
        return svcPairList;
        
    }
    
    public final List<String> getAllRecvSvcPair() {
        
        final List<String> svcPairList = new ArrayList<>();
        svcPairList.addAll(rawRecvMap.keySet());
        
        return svcPairList;
        
    }
    
    public final boolean isThisEmpty(final String pSvcPair, 
            final PathOptions.Direction pDirection) {
        
        switch(pDirection) {
            
            case RECV: 
                return rawRecvMap.isEmpty();
                
            case SEND:
                return rawSendMap.isEmpty();
        }
        
        return false;
    }
    
    public final boolean isThisPair(final String pSvcPair, 
            final PathOptions.Direction pDirection) {
        
        switch(pDirection) {
            
            case RECV:
                return rawRecvMap.containsKey(pSvcPair);
                
            case SEND:
                return rawSendMap.containsKey(pSvcPair);
                
        }
        
        return false;
    }
    
}
