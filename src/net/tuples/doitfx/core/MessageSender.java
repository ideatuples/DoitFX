/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author ideatuples
 */
public class MessageSender {
    
    private final AppInitialiser appInit;
    private final AccountConfigManager accConfigManager;
    private final SessionManager sessionManager;
    
    public MessageSender(final AppInitialiser pAppInit) {
        
        this.appInit = pAppInit;
        this.accConfigManager = this.appInit.getAccConfigManager();
        this.sessionManager = this.appInit.getSendSessionManager();
    }
    
    public final boolean sendMessage(final String pSvcPair, final MimeMessage pTgtMessage) {
        
        try {
            
            Transport.send(pTgtMessage);
            
            return true;
            
        } catch (MessagingException ex) {
            Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return false;
    }
    
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
    
    public final String getSenderAddr(final String pSvcPair) {
        
        final Properties tgtProps = accConfigManager.getRawRecvProps(pSvcPair);
        
        
        return tgtProps.getProperty("address");
    }
    
    public final String getSenderAlias(final String pSvcPair) {
        
        final Properties tgtProps = accConfigManager.getRawRecvProps(pSvcPair);
        
        
        return tgtProps.getProperty("alias");
    }
    
    public static final MessageAssembler 
        getMsgAssembler(final Session pTgtSession) {
        
        return new MessageAssembler(pTgtSession);
    }
}
