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

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import net.tuples.doitfx.connector.SessionManager;
import net.tuples.doitfx.connector.config.AccountConfigManager;
import net.tuples.doitfx.connector.config.SendPropsConverter;
import net.tuples.doitfx.core.utils.MessageAssembler;

/**
 * MessageSender is a manipulator of message receiving procedure.
 * 
 * This is very similar to TV remote controller.
 * It makes developers use DoitFX simply.
 * 
 * You can get MessageSender object from Connect object.
 * You don't need to create this object directly.
 * 
 * @author Geunatek Lee
 * @version 0.0.1, 26 Dec 2014
 * 
 **/
public class MessageSender {
    
    private final AppInitialiser appInit;
    private final AccountConfigManager accConfigManager;
    private final SessionManager sessionManager;
    
    public MessageSender(final AppInitialiser pAppInit) {
        
        this.appInit = pAppInit;
        this.accConfigManager = this.appInit.getAccConfigManager();
        this.sessionManager = this.appInit.getSendSessionManager();
    }
    
    /**
     * sendMessage method is the key method to send messages to somewhere.
     * 
     * However, Message object have to be provided by MessageAssembler,
     * a convenient class to assemble messages. 
     * 
     * @param pSvcPair The identifier for the account 
     * which is used for sending messages.
     * @param pTgtMessage The message that you want to send to.
     * @return True or False
     */
    public final boolean sendMessage(final String pSvcPair, final MimeMessage pTgtMessage) {
        
        try {
            
            Transport.send(pTgtMessage);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return false;
    }
    
    /**
     * Getting a Session object of an account, a convenient method.
     * 
     * @param pSvcPair The indicator of the account 
     * you want to get the a session.
     * @return Session object you selected.
     */
    public final Session getSession(final String pSvcPair) {
        
        final String accUUID;
        final Properties rawProps;
        final Properties actualProps;
        
        if(sessionManager.checkExistingSession(pSvcPair)) {
            
            return sessionManager.getExistingSession(pSvcPair);
        }
        
        rawProps = accConfigManager.getRawSendProps(pSvcPair);
        
        actualProps = SendPropsConverter.getActualProps(rawProps);
        accUUID = rawProps.getProperty("unique_id");
        
        return sessionManager.generateNewSession(pSvcPair, accUUID, actualProps);
    }
    
    /**
     * Getting Sender's E-Mail address.
     * 
     * As you know, almost all e-mail service providers have blocked to send 
     * E-Mails via their SMTP servers without authentication. This is for 
     * prevention of sending junk mails. 
     * 
     * Therefore, those service providers want to confirm E-Mail addresses 
     * whether E-Mails are coming from right users or not.
     * 
     * @param pSvcPair The indicator of the account users want to use.
     * @return An E-Mail address that was set by a user.
     */
    public final String getSenderAddr(final String pSvcPair) {
        
        final Properties tgtProps = accConfigManager.getRawRecvProps(pSvcPair);
        
        
        return tgtProps.getProperty("address");
    }
    
    /**
     * Getting alias of an user account.
     * 
     * People usually want to check easily where E-Mails are coming from.
     * Recognising people with senders' E-Mail address is quite hard.
     * 
     * So, JavaMail has a feature to send their real name or alias along with
     * the E-Mail address.
     * 
     * This method is for the feature.
     * 
     * @param pSvcPair
     * @return 
     */
    public final String getSenderAlias(final String pSvcPair) {
        
        final Properties tgtProps = accConfigManager.getRawRecvProps(pSvcPair);
        
        
        return tgtProps.getProperty("alias");
    }
    
    /**
     * Returning MessageAssembler.
     * 
     * Assembling messages is quite complex to use.
     * 
     * Furthermore, a message is consisted of multiple part.
     * 
     * With MessageAssembler object, you can do that more easier than 
     * when using JavaMail itself.
     * 
     * @param pTgtSession The session object of the account you need.
     * @return MessageAssembler
     */
    public static final MessageAssembler 
        getMsgAssembler(final Session pTgtSession) {
        
        return new MessageAssembler(pTgtSession);
    }
}
