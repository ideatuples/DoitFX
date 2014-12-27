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

package net.tuples.doitfx.connector.config.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * PathOptions is a sugar syntax class for getting basic information about
 * Configuration File Path and Directions
 * 
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * @see 
 * 
 **/

public class PathOptions {
    
    private final static Path confDirname = Paths.get(".test2");
    private final static Path userHomePathname = Paths.get(System.getProperty("user.home"));
    private final static Path defaultConfPathname = userHomePathname.resolve(confDirname);
    
    //private final AccountConfManager theLoader;
    //private final ConfigSaver theSaver;
    /**
     * This Direction enum class is to indicate transfer direction 
     * and get basic information about configuration path.
     * 
     * 
     */
    public static enum Direction {
        
        RECV("recv.props"), SEND("send.props");
        
        private final String filename;
        
        private Direction(String pFilename) {
            filename = pFilename;
        }
        
        /**
         * Getting the filename which is associate with the direction.
         * 
         * @return The filename by the transfer direction.
         */
        public String getDirection() {
            return filename;
        }
        
    }

    private PathOptions() {
        
    }
    
    /**
     * Returning default configuration path.
     * @return The default configuration path you've set.
     * 
     */
    public static final Path getDefaultConfigPath() {
        
        return defaultConfPathname;
    }
    
    
}
