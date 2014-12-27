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

package net.tuples.doitfx.connector.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.tuples.doitfx.connector.config.utils.PathOptions;

/**
 * AccountConfigLoader is to load pre-existing configuration files.
 * 
 * It loads all configuration files that were already set.
 * It uses ConfFileTraverser object that is inherited from SimpleFileVisitor class.
 * 
 * This class has 2 ConfFileTraverser, 1st is for files for receive configuration.
 * 2nd is for files send configuration.
 * 
 * @version 0.0.1, 26 Dec 2014
 * @see ConfFileTraverser
 * 
 */

public class AccountConfigLoader {
    
    
    private final ConfFileTraverser recvTraverser;
    private final ConfFileTraverser sendTraverser;
    
    private final Path defaultConfigPath;
    
    /**
     * The constructor of AccountConfigLoader
     * 
     */

    public AccountConfigLoader() {
        
        this.defaultConfigPath = PathOptions.getDefaultConfigPath();
        
        this.recvTraverser = new ConfFileTraverser(PathOptions.Direction.RECV.getDirection());
        this.sendTraverser = new ConfFileTraverser(PathOptions.Direction.SEND.getDirection());
        
    }
    
    /**
     * Getting mapping type raw properties for receive configurations.
     * 
     * @return Mapping type itself.
     */
    public Map<String, Properties> getRecvMap() {
        
        recvTraverser.clearTheMap();

        try {
            Files.walkFileTree(defaultConfigPath, recvTraverser);
        } catch (IOException ex) {
            Logger.getLogger(AccountConfigLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return recvTraverser.getTheMap();
    }
    
    /**
     * Getting mapping type raw properties for send configurations.
     * 
     * @return Mapping type itself.
     */
    
    public Map<String, Properties> getSendMap() {
        
        sendTraverser.clearTheMap();

        try {
            Files.walkFileTree(defaultConfigPath, sendTraverser);
        } catch (IOException ex) {
            Logger.getLogger(AccountConfigLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sendTraverser.getTheMap();
       
    }
    
       
}
