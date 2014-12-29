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

package net.tuples.doitfx.core.config;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.tuples.doitfx.connector.config.utils.PathOptions;

/**
 * AppConfigManager is a manager class for global configuration properties.
 * 
 * It doesn't have 100% perfect currently it feature
 * because there is no special options to use yet.
 * 
 * However, you can get AppID (The unique id of the application) 
 * to open KeyStore.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 **/

public class AppConfigManager {
    
    private static final Path appConfPathname = PathOptions
                .getDefaultConfigPath().resolve("app.config");
    
    private Properties appConfProps;
    
    private Reader appConfReader;
    private Writer appConfWriter;
    
    public AppConfigManager() {
        
        this.initialise();
        
    }
    
    private boolean initialise() {
        appConfProps = new Properties();
        loadTheProps();
        
        return true;
    }
    
    private boolean loadTheProps() {
        
        if(Files.notExists(appConfPathname)) {
            
            Properties initialProps = generateConfFile();
            appConfProps.putAll(initialProps);
            
            return true;
            
        } else {
            
            try {
                appConfReader = Files.newBufferedReader(appConfPathname, Charset.forName("US-ASCII"));
                appConfProps.load(appConfReader);
                appConfReader.close();
                
                return true;
            
            } catch (IOException ex) {
                Logger.getLogger(AppConfigManager.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
        
        return false;
    }
    
    private Properties generateConfFile() {
        
        final String generatedID;
        final Properties tempProps;        
        
        final Writer mainPropsWriter;
        
        tempProps = new Properties();
        
        
        try {
            mainPropsWriter = Files
                    .newBufferedWriter(appConfPathname, Charset.forName("US-ASCII"));
            
            generatedID = UUID.randomUUID().toString();
            
            tempProps.setProperty("unique_id", generatedID);
            
            tempProps.store(mainPropsWriter, null);
            mainPropsWriter.close();
            
            return tempProps;
            
        } catch (IOException ex) {
            Logger.getLogger(AppConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return null;
    }
    
    /**
     * Getting a global property.
     * 
     * @param pPropertyKey The name of a property key.
     * @return The value of the property key.
     */
    public String getPropertyValue(final String pPropertyKey) {
        
        return appConfProps.getProperty(pPropertyKey);
    }
    
    /**
     * Listing all keys of the properties.
     * 
     * @return A List object that contains key names.
     */
    public List<String> getAllPropertyKeys() {
        
        final List<String> keyList;
        
        keyList = new ArrayList<>();
        
        for(Object key : appConfProps.keySet()) {
            if (key instanceof String) {
                keyList.add(key.toString());
            }
        }
        
        return keyList;
    }
    
}
