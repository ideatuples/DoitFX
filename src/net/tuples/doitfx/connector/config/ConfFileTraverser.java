/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author ideatuples
 */
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
    
    
    public final Map<String, Properties> getTheMap() {
        
        return rawMap;
    }
    
    public final boolean clearTheMap() {
        
        rawMap.clear();
        
        return true;
    }
    
}
