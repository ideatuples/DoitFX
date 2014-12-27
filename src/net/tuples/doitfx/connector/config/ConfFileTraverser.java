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
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import net.tuples.doitfx.core.utils.StringChunk;

/**
 * ConfFileTraverser class is to retrieve the directory which is storing configurations.
 * 
 * ConfFileTranverser class is inherited SimpleFileVisitor 
 * which is a part of Java NewIO. 
 * 
 * Once, it start to traverse the target directory, you can get the information 
 * all about the directory or entire directories under the target directory.
 * 
 * Recursive function starts at the point of calling the constructor and
 * when it is finished, store the information into a Map collection to transfer 
 * the result.
 * 
 * You don't need to think about non-implemented methods inherited from the visitor.
 * 
 * The only thing you have to know is getTheMap and clearTheMap.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * 
 **/
public class ConfFileTraverser extends SimpleFileVisitor<Path> {
    
    private final String theFilename;
    
    private Reader propsReader;
    private Map<String, Properties> rawMap;
    
    
    public ConfFileTraverser(final String pFilename) {
        this.theFilename = pFilename;
        this.initialise();
        
    }
    
    private boolean initialise()  {
        propsReader = null;
        rawMap = new HashMap<>();
        
        return true;
    }
    
    
    @Override
    public FileVisitResult preVisitDirectory(final Path pTargetPath, final BasicFileAttributes attrs) {
        
        return FileVisitResult.CONTINUE;
    }
    
    /**
     * This is the method to traverse the target directory.
     * It works with Files.walkFileTree method 
     * which is called in AccountConfigLoader object.
     * 
     * During the action of the method, this method collects all configurations 
     * and store them into a Map collection.
     * 
     * @param pTargetFile It is the file name that ConfFileTraverser object stays currently.
     * @param attrs Doesn't matter too much, forget it!.
     * 
     * @return FileVisitResult object, but we don't use this result.
     * @throws IOException 
     */
    
    @Override
    public FileVisitResult visitFile(final Path pTargetFile, final BasicFileAttributes attrs) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        final String filename = pTargetFile.getFileName().toString();
        final Path filePath = Paths.get(theFilename);
        
        if (!filename.equalsIgnoreCase(theFilename)) {
            return FileVisitResult.CONTINUE;
        }
            
        final int nameCount = pTargetFile.getNameCount();
        
        final StringChunk svcPairKeyChunk;
        final Properties rawConfProps = new Properties();
        
        final String svcName =  pTargetFile.getName(nameCount-3).toString();
        final String userName = pTargetFile.getName(nameCount-2).toString();
        
        
        svcPairKeyChunk = new StringChunk(svcName);
        svcPairKeyChunk.mewingCat("/", userName);
        
        
        propsReader = Files.newBufferedReader(pTargetFile, Charset.forName("US-ASCII"));
        rawConfProps.load(propsReader);
        
        
        propsReader.close();
        
        rawMap.put(svcPairKeyChunk.toString(), rawConfProps);
        
        
        return FileVisitResult.CONTINUE;
    }
    
    @Override
    public FileVisitResult postVisitDirectory(final Path pTaretPath, final IOException ex) {
        
        return FileVisitResult.CONTINUE;
    }
    
    /**
     * This method is to return the Map collection.
     * 
     * @return 
     */
    public final Map<String, Properties> getTheMap() {
        
        return rawMap;
    }
    
    /**
     * 
     * Clearing the Map collection to initialise again.
     * 
     * @return True or False
     */
    public final boolean clearTheMap() {
        
        rawMap.clear();
        
        return true;
    }
    
}
