/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import net.tuples.doitfx.connector.Connector;

/**
 *
 * @author ideatuples
 */
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
    
    public final List<Folder> getEntireFolder(final String pSvcPair) {
        
        final Folder rootFolder;
        final List<Folder> subFolders;
        
        try {
            rootFolder = getRootFolder(pSvcPair);
            subFolders = Arrays.asList(rootFolder.list());
            
            return subFolders;
            
        } catch (MessagingException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return null;
    }
    
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
    
    public final int getFolderCounts(final String pSvcPair) {
        
        List<Folder> subFolders = getEntireFolder(pSvcPair);
        
        return subFolders.size();
    }
    
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
    
    public static class FolderManipulator { 
        
        private FolderManipulator() {
            
        }
        
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
