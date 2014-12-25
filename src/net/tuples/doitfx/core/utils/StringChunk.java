/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.core.utils;

/**
 *
 * @author ideatuples
 */
public class StringChunk {
    
    public static final String colon = ":";
    public static final String semiColon = ";";
    public static final String comma = ",";
    public static final String dot = ".";
    public static final String atMark = "@";
    public static final String qMark = "?";
    public static final String slash = "/";
    
    private String delimiter;
    private String sourceChunk;
    
    private String generatedChunkString;
    
    public StringChunk(final String pInitialChunk) {
        this.sourceChunk = pInitialChunk;
        this.generatedChunkString = this.sourceChunk;
        
    }
    
    public final StringChunk mewingCat(final String pDelimiter, final String pTheChunk) {
        generatedChunkString = generatedChunkString.concat(pDelimiter).concat(pTheChunk);
        
        return this;
    }
   
    
    @Override
    public final String toString() {
        
        return generatedChunkString;
    }
    
    public boolean flush() {
        delimiter = null;
        sourceChunk = null;
        
        return true;
    }
    
    
}
