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
 * MessageAssembler is a convenient class to assemble parts of a message.
 * 
 * As you know, an E-Mail message is consisted of multiple part.
 * So, a part in a message can be for file attachments or plain text.
 * 
 * This was written following the procedure sending E-Mails we have known.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 **/

public class MessageAssembler {

    private static final Charset msgEncoding = Charset.forName("UTF-8");
    
    private final MimeMessage theMessage;
    private final MimeMultipart thePartSet;
    
    
    /**
     * The constructor of MessageAssembler.
     * 
     * MessageAssmbler has to have a Session object to initialise Message class.
     * 
     * @param pTgtSession 
     */
    public MessageAssembler(final Session pTgtSession) {
        this.theMessage = new MimeMessage(pTgtSession);
        this.thePartSet = new MimeMultipart();
    }
    
    /**
     * Setting the subject onto a message.
     * 
     * @param pSubjectStr Setting the subject of a mail.
     * @return True or False
     */
    public final boolean setSubject(final String pSubjectStr) {
        
        try {
            theMessage.setSubject(pSubjectStr, msgEncoding.displayName());
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    /**
     * Setting sender's E-Mail address.
     * 
     * Set a sender's e-mail address onto a message.
     * Commonly, a user has many different accounts.
     * 
     * So, the sender's e-mail address is up to their mind 
     * which account they go to use.
     * 
     * @param pSenderAddr The address you got from MessageSender object.
     * @return True or False
     */
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
    
    /**
     * Setting a sender's E-Mail address.
     * 
     * Set a sender's e-mail address onto a message.
     * Commonly, a user has many different accounts.
     * 
     * So, the sender's e-mail address is up to their mind 
     * which account they go to use.
     *
     * @param pSenderAddr The sender's e-mail address.
     * @param pAlias An alias or real name of the sender.
     * 
     * 
     */
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
    
    /**
     * 
     * Setting a recipient of a message.
     * 
     * Set a recipient of a message.
     * 
     * @param pRType Whether the type of the recipient is To, Carbon Copied or 
     * Blinded Carbon Copied
     * @param pRecipentAddrStr The E-Mail address of a recipient.
     * @return 
     */
    public final boolean addRecipient(final Message.RecipientType pRType, 
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
    
    /**
     * 
     * Setting multiple recipient onto a message.
     * 
     * Set message recipients at the same time.
     * 
     * @param pRType Whether the type is To, Carbon Copied or 
     * Blinded Carbon Copied.
     * @param pRecipentQueue A queue collection that contains recipients address
     * to forward to this method.
     * @return True or False.
     */
    public final boolean addRecipients(final Message.RecipientType pRType, 
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
    
    /**
     * Adding a part.
     * 
     * @param pTgtPart The part that a user wants to add.
     * @return True or False.
     */
    public final boolean addPart(final BodyPart pTgtPart) {
        
        try {
            thePartSet.addBodyPart(pTgtPart);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    /**
     * Adding a part with a index value.
     * 
     * @param pTgtPart The part object to be add.
     * @param pPartIndex The index value to be used to append a part by.
     * @return True or False.
     */
    public final boolean addPart(final BodyPart pTgtPart, final int pPartIndex) {
        
        
        try {
            thePartSet.addBodyPart(pTgtPart, pPartIndex);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    /**
     * Adding multi part onto a message.
     * 
     * @param pBodyParts A queue object to be added onto a message.
     * @return True or False
     */
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
    
    /*
    public final boolean deletePart(final BodyPart pTgtPart) {
        
        
        try {
            thePartSet.removeBodyPart(pTgtPart);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    */
    
    /**
     * Deletion for a part with an index value.
     * 
     * @param pPartIndex The index value of the part a user want to delete.
     * @return True or False.
     */
    public final boolean deletePart(final int pPartIndex) {
        
        try {
            thePartSet.removeBodyPart(pPartIndex);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    /**
     * Generate new text part.
     * 
     * @param pMsgText The text that a user want to add.
     * @param pMimeType A type of the MIME type. (e.g. text/plain or text/html)
     * @return MimeBodyPart type Part object.
     */
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
    
    /**
     * Generate new file attachment part.
     * 
     * @param pAttachingFilePath The location of a file to be attached.
     * @return MimeBodyPart type Part object.
     */
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
    
    
    /**
     * Returning assembled message to the type of MimeMessage.
     * 
     * @return MimeMessage class type assembled message. 
     */
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
