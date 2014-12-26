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

package net.tuples.doitfx.connector.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.tuples.doitfx.connector.config.utils.PathOptions;


/**
 * AccountConfigManager class is to manage entire configuration 
 * for both send and receive.
 * 
 * Primarily, we need to invoke when we use the library.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * @see AccountConfigLoader
 * @see AccountConfigSaver
 * 
 **/
public class AccountConfigManager {
    
    
    private final AccountConfigLoader accConfigLoader;
    private final AccountConfigSaver accConfigSaver;
    
    private Map<String, Properties> rawRecvMap;
    private Map<String, Properties> rawSendMap;

    /**
     * The constructor of the class
     * 
     */
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
    
    /**
     * If you go to append new Properties object for newly added account,
     * use this method 
     * 
     * @param pSvcPair The indicator of the account you want append.
     * @param pDirection Send or Receive
     * @param pTgtProps The Properties class you filled about the account.
     * 
     * @return True or False
     */
    public final boolean setNewProps(final String pSvcPair,
            final PathOptions.Direction pDirection, final Properties pTgtProps) {
        
        accConfigSaver.setProps(pSvcPair, pDirection, pTgtProps);
        replaceOneProps(pSvcPair, pDirection, pTgtProps);
        
        return true;
    }
    /*
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
    */
    
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
