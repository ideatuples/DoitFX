/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.config.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author ideatuples
 */
public class PathOptions {
    
    private final static Path confDirname = Paths.get(".test2");
    private final static Path userHomePathname = Paths.get(System.getProperty("user.home"));
    private final static Path defaultConfPathname = userHomePathname.resolve(confDirname);
    
    //private final AccountConfManager theLoader;
    //private final ConfigSaver theSaver;
    
    public static enum Direction {
        
        RECV("recv.props"), SEND("send.props");
        
        private final String filename;
        
        private Direction(String pFilename) {
            filename = pFilename;
        }
        
        public String getDirection() {
            return filename;
        }
        
    }

    private PathOptions() {
        
    }
    
    public static final Path getDefaultConfigPath() {
        
        return defaultConfPathname;
    }
    
    
}
