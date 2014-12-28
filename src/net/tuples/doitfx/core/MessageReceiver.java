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

package net.tuples.doitfx.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;
import net.tuples.doitfx.connector.ConnectManager;



/**
 * MessageReceiver is a manipulator of message receiving procedure.
 * 
 * This is very similar to TV remote controller.
 * It makes developers use DoitFX simply.
 * 
 * You can get MessageReceiver object from Connect object.
 * You don't need to create this object directly.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * 
 **/

public class MessageReceiver {
    private final AppInitialiser appInit;
    private final ConnectManager connectManager;

    public MessageReceiver(final AppInitialiser pAppInit) {
        this.appInit = pAppInit;
        this.connectManager = this.appInit.getConnectManager();
    }
    
    private Folder getRootFolder(final String pSvcPair) {
    
        final Store tgtStore;
        final Folder rootFolder;
        
        if(!connectManager.checkExistingStore(pSvcPair)) {
            
            StringBuilder msgBuilder = 
                    new StringBuilder("There is no store which name is")
                            .append(" ")
                            .append(pSvcPair);
                        
            throw new NullPointerException(msgBuilder.toString());
        }
        
        tgtStore = connectManager.getExistingStore(pSvcPair);
        
        try {
            rootFolder =  tgtStore.getDefaultFolder();
            
            return rootFolder;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
     * Getting entire folder list of specific SvcPair.
     * 
     * @param pSvcPair The indicator of an e-mail account.
     * @return List of Folder object.
     */
    public final List<Folder> getEntireFolder(final String pSvcPair) {
        
        final Folder rootFolder;
        final List<Folder> subFolders;
        
        try {
            rootFolder = getRootFolder(pSvcPair);
            subFolders = Arrays.asList(rootFolder.list());
            
            return subFolders;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return null;
    }
    
    /**
    * Getting one folder of specific SvcPair you want.
    * 
    * @param pSvcPair The indicator of an e-mail account.
    * @return Specific Folder object.
    */
    public final Folder getFolder(final String pSvcPair, final String pFolderName) {
        
        final Folder rootFolder;
        final Folder tgtFolder;
        
        rootFolder = getRootFolder(pSvcPair);
        
        try {
            tgtFolder = rootFolder.getFolder(pFolderName);
            return tgtFolder;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
    * Getting counts of Folder objects.
    * 
    * @param pSvcPair The indicator of an e-mail account.
    * @return counts of Folder objects.
    */
    public final int getFolderCounts(final String pSvcPair) {
        
        List<Folder> subFolders = getEntireFolder(pSvcPair);
        
        return subFolders.size();
    }
    
    /**
     * Getting entire folder name String objects of specific SvcPair.
     * 
     * @param pSvcPair The indicator of an e-mail account.
     * @return List of String object of folders.
     */
    public final List<String> getFolderNames(final String pSvcPair) {
        
        final List<Folder> subFolders;
        final List<String> subFolderNames;
        
        subFolders = getEntireFolder(pSvcPair);
        subFolderNames = new ArrayList<>();
        
        for(Folder eachFolder : subFolders) {
            
            subFolderNames.add(eachFolder.getFullName());
            
        }
        
        return subFolderNames;
    }
    
    /**
     * FolderManipulator is a manipulator of every Folder object.
     * 
     * If you go to create, update, delete a folder, you need to have this 
     * manipulator object to do them.
     * 
     * You don't need to create this by your hands.
     * This class is a set of static methods.
     * 
     * @author Geunatek Lee
     * @version 0.0.1, 26 Dec 2014
     * 
     **/
    public static class FolderManipulator { 
        
        private FolderManipulator() {
            
        }
        
        /**
         * Checkout folder name which is already generated.
         * 
         * @param pRootFolder Root folder means the folder you get 
         * with the method of MessageReceiver.getRootFolder()
         * 
         * @param pFolderName The folder name that you are looking for.
         * @return True or False
         */
        public static final boolean 
            checkExistingFolder(final Folder pRootFolder, final String pFolderName) {
            
            final Folder[] subFolders;
            
            try {
                
                subFolders = pRootFolder.list();
                
                for(Folder eachFolder : subFolders) {
                    
                    if(eachFolder.getFullName().equals(pFolderName)) {
                        return true;
                    }
                        
                }
            } catch (MessagingException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            return false;
        }
        
        /**
         * Generate new folder
         * 
         * @param pTgtStore 
         * @param pFolderName The folder name that you want to name this folder.
         * 
         * @return True or False
         */
        public static final boolean generate(final Store pTgtStore, final String pFolderName) {
            
            final Folder rootFolder;
            final Folder theNewFolder;
            
            
            try {
                boolean theExistingResult = false;
                
                rootFolder = pTgtStore.getDefaultFolder();
                theExistingResult = FolderManipulator.checkExistingFolder(rootFolder, pFolderName);
                
                if(!theExistingResult) {
                   
                    theNewFolder = pTgtStore.getFolder(pFolderName);
                    
                    return theNewFolder.create(Folder.HOLDS_MESSAGES);
                }
                
            } catch (MessagingException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return false;
        }
        
        /**
         * Delete a folder that has been existed on account.
         * 
         * @param pTgtStore
         * @param pFolderName The name of the folder.
         * @param pIsRecursive Check to get confirm to delete including
         * sub-directories.
         * @return True or False
         */
        public static final boolean 
            delete(final Store pTgtStore, 
                    final String pFolderName, final boolean pIsRecursive) {
            
            final Folder tgtFolder;
            
            try {
                tgtFolder = pTgtStore.getFolder(pFolderName);

                if(!tgtFolder.exists()) {

                    StringBuilder messageBuilder = 
                            new StringBuilder("There is no folder which is named");

                    messageBuilder.append(" ").append(pFolderName);

                    throw new NullPointerException(messageBuilder.toString());
                }

                return tgtFolder.delete(pIsRecursive);


            } catch (MessagingException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        }
        
        /**
         * Rename the name of a folder.
         * 
         * @param pTgtStore
         * @param pSrcFolderName The original folder name
         * @param pTgtFolderName The name you want to rename.
         * @return True or False
         */
        public static final boolean 
            rename(final Store pTgtStore, 
                    final String pSrcFolderName, final String pTgtFolderName) {
            
            final Folder rootFolder;
            final Folder srcFolder;
            final Folder tgtFolder;
            
            try {
                rootFolder = pTgtStore.getDefaultFolder();
                srcFolder = rootFolder.getFolder(pSrcFolderName);
                tgtFolder = pTgtStore.getFolder(pTgtFolderName);
                
                if(!srcFolder.exists()) {
                    
                    StringBuilder messageBuilder = 
                            new StringBuilder("There is no folder which is named");

                    messageBuilder.append(" ").append(pSrcFolderName);

                    throw new NullPointerException(messageBuilder.toString());
                }
                
                return srcFolder.renameTo(tgtFolder);
                
            } catch (MessagingException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return false;
        }
        
        
        public static final int getSubFolderCounts(final Store pTgtStore) {

            final Folder rootFolder;
            final List<Folder> subFolders;

            try {
                rootFolder = pTgtStore.getDefaultFolder();
                subFolders = Arrays.asList(rootFolder.list());
                
                return subFolders.size();
                
            } catch (MessagingException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return -1;
        }
        
    }
    
}
