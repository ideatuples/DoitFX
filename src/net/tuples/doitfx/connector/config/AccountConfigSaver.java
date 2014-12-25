/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.config;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.tuples.doitfx.connector.config.utils.PathOptions;

/**
 *
 * @author ideatuples
 */
public class AccountConfigSaver {
    
    private Writer propsWriter;
    private final Path defaultConfigPath;

    public AccountConfigSaver() {
        
        this.defaultConfigPath = PathOptions.getDefaultConfigPath();
        this.initialise();
    }
    
    private boolean initialise() {
        
        propsWriter = null;
        
        return true;
        
    }
    
    private boolean storeProps(final Path pConfFilePath, final Properties pTgtProperties) {
        
        System.out.println(pConfFilePath.toString());
        
        final Path theParentPath = pConfFilePath.getParent();
        
        if(Files.notExists(pConfFilePath)) {
            try {
                
                Files.createDirectories(theParentPath);
            
            } catch (IOException ex) {
                Logger.getLogger(AccountConfigSaver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            propsWriter = Files.newBufferedWriter(pConfFilePath, 
                    Charset.forName("US-ASCII"), StandardOpenOption.CREATE);
            
            pTgtProperties.store(propsWriter, null);
            
            propsWriter.close();
            
            return true;
            
        } catch (IOException ex) {
            Logger.getLogger(AccountConfigSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public final boolean setProps(final String pSvcPair,
            final PathOptions.Direction pDirection, final Properties pTgtProperties) {
        
        final String confFilename;
        final Path confFilePath;
        final Path fullConfFilePath;
        
        confFilename = pDirection.getDirection();
        confFilePath = Paths.get(pSvcPair, pDirection.getDirection());
        fullConfFilePath = defaultConfigPath.resolve(confFilePath);
        
        return storeProps(fullConfFilePath, pTgtProperties);
    
    }
    
    
    
}
