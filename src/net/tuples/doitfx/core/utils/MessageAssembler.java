/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.core.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ideatuples
 */
public class MessageAssembler {

    private static final Charset msgEncoding = Charset.forName("UTF-8");
    
    private final MimeMessage theMessage;
    private final MimeMultipart thePartSet;
    
    public MessageAssembler(final Session pTgtSession) {
        this.theMessage = new MimeMessage(pTgtSession);
        this.thePartSet = new MimeMultipart();
    }
    
    public final boolean setSubject(final String pSubjectStr) {
        
        try {
            theMessage.setSubject(pSubjectStr, msgEncoding.displayName());
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public final boolean setSenderAddr(final String pSenderAddr) {
        
        final Address senderAddr;
        
        try {
            senderAddr = new InternetAddress(pSenderAddr);
            theMessage.setFrom(senderAddr);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public final boolean setSenderAddr(final String pSenderAddr, 
            final String pAlias) {
        
        final InternetAddress senderAddr;
        
        try {
            senderAddr = new InternetAddress(pSenderAddr);
            senderAddr.setPersonal(pAlias, msgEncoding.displayName());
            
            theMessage.setFrom(senderAddr);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public final boolean addRecipent(final Message.RecipientType pRType, 
            final String pRecipentAddrStr) {
        
        final InternetAddress theRecipentAddr;
        
        try {
            
            theRecipentAddr = new InternetAddress(pRecipentAddrStr);
            theMessage.addRecipient(pRType, theRecipentAddr);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public final boolean addRecipents(final Message.RecipientType pRType, 
            final Queue<String> pRecipentQueue) {
        
        final int queueSize;
        final InternetAddress[] theRecipentAddrs;
       
        queueSize = pRecipentQueue.size();
        theRecipentAddrs = new InternetAddress[queueSize];
        
        try {
            
            for(int i=0; i<=queueSize-1; i++) {
                theRecipentAddrs[i] = new InternetAddress(pRecipentQueue.poll());
                
            }
            
            return true;
               
        } catch (AddressException ex) {
                Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    
    public final boolean addPart(final BodyPart pTgtPart) {
        
        try {
            thePartSet.addBodyPart(pTgtPart);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public final boolean addPart(final BodyPart pTgtPart, final int pPartIndex) {
        
        
        try {
            thePartSet.addBodyPart(pTgtPart, pPartIndex);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public final boolean addPart(Queue<BodyPart> pBodyParts) {
        
        final int queueSize;
        
        queueSize = pBodyParts.size();
        
        
        try{
            for(int i=0; i<queueSize; i++) {
                thePartSet.addBodyPart(pBodyParts.poll());
            }
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public final boolean deletePart(final BodyPart pTgtPart) {
        
        
        try {
            thePartSet.removeBodyPart(pTgtPart);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public final boolean deletePart(final int pPartIndex) {
        
        try {
            thePartSet.removeBodyPart(pPartIndex);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public static final MimeBodyPart newTextPart(final String pMsgText, 
            final String pMimeType) {
        
        final MimeBodyPart tgtBodyPart;
        
        tgtBodyPart = new MimeBodyPart();
        
        try {
            tgtBodyPart.setContent(pMsgText, pMimeType);
            tgtBodyPart.setHeader("Charset", "UTF-8");
            
            return tgtBodyPart;
                    
        } catch (MessagingException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    public static final MimeBodyPart newAttachementPart(final Path pAttachingFilePath) {
        
        final MimeBodyPart tgtBodyPart;
        
        tgtBodyPart = new MimeBodyPart();
        
        
        try {
            tgtBodyPart.attachFile(pAttachingFilePath.toFile());
            
            return tgtBodyPart;
            
        } catch (IOException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public final MimeMessage getAssembledMsg() {
        try {
            theMessage.setContent(thePartSet);
            
            return theMessage;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
