/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author ideatuples
 */
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
    
    public String getPropertyValue(final String pPropertyKey) {
        
        return appConfProps.getProperty(pPropertyKey);
    }
    
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
