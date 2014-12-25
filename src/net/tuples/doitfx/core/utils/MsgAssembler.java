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
public class MsgAssembler {
    
    private final MimeMessage theMessage;
    private final MimeMultipart thePartSet;
    
    public MsgAssembler(final Session pTgtSession) {
        
        theMessage = new MimeMessage(pTgtSession);
        thePartSet = new MimeMultipart();
        
    }
    
    public final Address setSenderAddr(final String pSenderAddrStr) {
        try {
            
            return new InternetAddress(pSenderAddrStr);
            
        } catch (AddressException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public final Address setSenderAddr(final String pSenderAddrStr, 
            final String pSenderAlias) {
        
        final InternetAddress senderAddress;
        
        try {
            senderAddress = new InternetAddress(pSenderAddrStr);
            senderAddress.setPersonal(pSenderAlias, Charset.defaultCharset().displayName());
            
            return senderAddress;
            
        } catch (AddressException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public final boolean addRecipent(final Message.RecipientType pRType, 
            final String pRecipentAddrStr) {
        
        final Address theRecipentAddr;
        
        try {
            
            theRecipentAddr = new InternetAddress(pRecipentAddrStr);
            
            theMessage.addRecipient(pRType, theRecipentAddr);
            
        } catch (MessagingException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return true;
    }
    
    public final boolean addRecipents(final Message.RecipientType pRType, 
            final Queue<String> pRecipentQueue) {
        
        final int queueSize;
        final Address[] theRecipentAddrs;
        
        queueSize = pRecipentQueue.size();
        theRecipentAddrs = new Address[queueSize];
        
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
    
    public final boolean addPart(final BodyPart pTgtPart, final int pPartIndex) {
        
        try {
            thePartSet.addBodyPart(pTgtPart, pPartIndex);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    
    public final boolean addPart(final BodyPart pTgtPart) {
        
        try {
            thePartSet.addBodyPart(pTgtPart);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
        
    public final MimeBodyPart generateNewPart(final String pMsgText, 
            final String pMimeType) {
        
        final MimeBodyPart tgtBodyPart;
        
        tgtBodyPart = new MimeBodyPart();
        
        try {
            tgtBodyPart.setContent(pMsgText, pMimeType);
            
            return tgtBodyPart;
                    
        } catch (MessagingException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    public final MimeBodyPart generateNewPart(final Path pAttachingFilePath) {
        
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
    
    
    public final Message getAssembledMsg() {
        
        
        try {
            theMessage.setContent(thePartSet);
            
            return theMessage;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MsgAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

}
