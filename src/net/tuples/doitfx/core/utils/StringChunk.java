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

package net.tuples.doitfx.core.utils;

/**
 * StringChunk is a modified version of StringBuilder class.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 **/
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
    
    /**
     * The constructor of StringChunk class.
     * 
     * @param pInitialChunk The initial value of a StringChunk object.
     */
    public StringChunk(final String pInitialChunk) {
        this.sourceChunk = pInitialChunk;
        this.generatedChunkString = this.sourceChunk;
        
    }
    
    /**
     * Adding a String following a delimiter.
     * 
     * @param pDelimiter A delimiter like a dot or semi-colon.
     * @param pTheChunk A String which will be appended.
     * @return StringChunk object that has a delimiter and a String 
     * that was appended.
     */
    public final StringChunk mewingCat(final String pDelimiter, final String pTheChunk) {
        generatedChunkString = generatedChunkString.concat(pDelimiter).concat(pTheChunk);
        
        return this;
    }
   
    /**
     * Returning a String that contains all Strings which were assembled.
     * @return Final String value of multiple String.
     */
    @Override
    public final String toString() {
        
        return generatedChunkString;
    }
    
    /**
     * Clearing all Strings and making it initialise.
     * @return True or False.
     */
    public boolean flush() {
        delimiter = null;
        sourceChunk = null;
        
        return true;
    }
    
    
}
