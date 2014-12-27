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
 * AccountConfigSaver class is to store newly generated account configuration.
 * 
 * This class is designed for storing new account configuration safely.
 * It is at opposite position of AccountConfigLoader class.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * 
 **/
public class AccountConfigSaver {
    
    private Writer propsWriter;
    private final Path defaultConfigPath;
    
    /**
     * The constructor of the class.
     * 
     * There is no need to put parameters
     * into when you create AccountConfigSaver.
     */

    public AccountConfigSaver() {
        
        this.defaultConfigPath = PathOptions.getDefaultConfigPath();
        this.initialise();
    }
    
    private boolean initialise() {
        
        propsWriter = null;
        
        return true;
        
    }
    
    private boolean storeProps(final Path pConfFilePath, final Properties pTgtProperties) {
        
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
    
    /**
     * 
     * @param pSvcPair The account indicator you want to generate.
     * @param pDirection You have to decide its transfer direction, Send or Recv.
     * @param pTgtProperties A Properties object you did fill with account information.
     * 
     * @return True or False
     */
    
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
