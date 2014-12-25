/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.tuples.doitfx.connector.config.utils.PathOptions;

/**
 *
 * @author ideatuples
 */
public class AccountConfigLoader {
    
    
    private final ConfFileTraverser recvTraverser;
    private final ConfFileTraverser sendTraverser;
    
    private final Path defaultConfigPath;

    public AccountConfigLoader() {
        
        this.defaultConfigPath = PathOptions.getDefaultConfigPath();
        
        this.recvTraverser = new ConfFileTraverser(PathOptions.Direction.RECV.getDirection());
        this.sendTraverser = new ConfFileTraverser(PathOptions.Direction.SEND.getDirection());
        
    }
    
    public Map<String, Properties> getRecvMap() {
        
        recvTraverser.clearTheMap();

        try {
            Files.walkFileTree(defaultConfigPath, recvTraverser);
        } catch (IOException ex) {
            Logger.getLogger(AccountConfigLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return recvTraverser.getTheMap();
    }
    
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
