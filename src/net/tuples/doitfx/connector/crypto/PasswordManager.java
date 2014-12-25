/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.crypto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

import net.tuples.doitfx.connector.config.utils.PathOptions;
import net.tuples.doitfx.connector.crypto.utils.KeyByteGenerator;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author ideatuples
 */
public class PasswordManager {
    
    private static final Path passcodeFilename = Paths.get("pass.code");
    private final Path pwStorePath;
    private final PasswordCodec pwCodec;
    
    private final String pwStorePassword;
    
    private KeyStore pwStore;
    

    public PasswordManager(final String pAppID) {
        pwCodec = new PasswordCodec();
        pwStorePath = PathOptions.getDefaultConfigPath().resolve("TheStore.dat");
        
        pwStorePassword = pAppID;
        
        loadPWStore(pwStorePath);
        
    }
    
    private boolean loadPWStore(Path pStorePath) {
       
        final InputStream storeInStm;
        
        if(Files.notExists(pStorePath)) {
            generateNewPWStore(pStorePath);
            
            return true;
        } 
            
        try {
            storeInStm = new FileInputStream(pwStorePath.toFile());
            
            pwStore = KeyStore.getInstance("JCEKS");
            pwStore.load(storeInStm, null);

            storeInStm.close();

            return true;

        } catch (IOException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
         
        return false;
    }
    
    private boolean generateNewPWStore(Path pStorePath) {
        
        final OutputStream storeOutStm;
        
        try {
            storeOutStm = Files.newOutputStream(pStorePath, StandardOpenOption.CREATE);
            
            pwStore = KeyStore.getInstance("JCEKS");
            
            pwStore.load(null, null);
            pwStore.store(storeOutStm, pwStorePassword.toCharArray());
            
            storeOutStm.close();
            
            return true;
            
        } catch (IOException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return false;
    }
    
    
    private byte[] loadPasscode(final String pSvcPair) {
        
        final FileInputStream passCodeFileInStm;
        final DataInputStream passCodeDataInStm;
        
        final byte[] bufferByteArray = new byte[64];
        final byte[] decodedByteArray;
        
        final int MAX_BYTES_AT_ONCE = 8;
        
        
        final Path passCodeLocation = PathOptions.getDefaultConfigPath()
                .resolve(pSvcPair)
                .resolve(passcodeFilename);
        
        try {
            passCodeFileInStm = new FileInputStream(passCodeLocation.toFile());
            passCodeDataInStm = new DataInputStream(passCodeFileInStm);
            
            int counts = 0;
            int size = 0;
            
            while(true) {
                size = passCodeDataInStm.read(bufferByteArray, counts, MAX_BYTES_AT_ONCE);
                
                if (size<=0) {
                    break;
                }
                
                counts += size;
                size = 0;
            }
            
            passCodeDataInStm.close();
            passCodeFileInStm.close();
            
            decodedByteArray = Base64.decodeBase64(bufferByteArray);
            
            return decodedByteArray;
            
        } catch (IOException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private boolean savePasscode(final String pSvcPair, final byte[] pPasscodeByteArray) {
        
        final FileOutputStream passcodeFileOutStm;
        final DataOutputStream passcodeDataOutStm;
        
        final byte[] encodedByteArray;
        
        final Path passCodeLocation = PathOptions.getDefaultConfigPath()
                .resolve(pSvcPair)
                .resolve(passcodeFilename);
        
        encodedByteArray = Base64.encodeBase64(pPasscodeByteArray);
        
        try {
            passcodeFileOutStm = new FileOutputStream(passCodeLocation.toFile());
            passcodeDataOutStm = new DataOutputStream(passcodeFileOutStm);
            
            passcodeDataOutStm.write(encodedByteArray);
           
            passcodeDataOutStm.close();
            passcodeFileOutStm.close();
            
        } catch (IOException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return false;
    }
    
    private boolean checkAlias(final String pAlias) {
        
        try {
            
            return pwStore.containsAlias(pAlias);
            
        } catch (KeyStoreException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
   
    
    public final boolean setAccountKey(final String pSvcPair, 
            final String pAccID, final String pPlainText) {
        
        final byte[] plainByteArray;
        final byte[] encryptedByteArray;
        
        final SecretKey theKey;
        final KeyStore.Entry theKeyEntry;
        final KeyStore.ProtectionParameter protectionParam;
        
        final FileOutputStream storeOutStm;
        
        if(!checkAlias(pSvcPair)) {
            System.err.println("There is no alias same as the alias");
        }
        
        plainByteArray = pPlainText.getBytes(Charset.forName("UTF-8"));
        
        theKey = KeyByteGenerator.activateKeyGeneration();
        theKeyEntry = new KeyStore.SecretKeyEntry(theKey);
        protectionParam = new KeyStore.PasswordProtection(pAccID.toCharArray());
        
        encryptedByteArray = pwCodec.activateEncryption(theKey, plainByteArray);
        
        
        try {
            storeOutStm = new FileOutputStream(pwStorePath.toFile());
            
            pwStore.setEntry(pSvcPair, theKeyEntry, protectionParam);
            pwStore.store(storeOutStm, pwStorePassword.toCharArray());
            
            storeOutStm.close();
            
        } catch (IOException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        savePasscode(pSvcPair, encryptedByteArray);
        
        return true;
        
    }
    
    public final String getAccountKey(final String pSvcPair, final String pAccID) {
        
        final byte[] decodedByteArray;
        final byte[] decryptedByteArray;
        
        final SecretKey theSecretKey;
        final KeyStore.Entry keyEntry;
        final KeyStore.SecretKeyEntry secretKeyEntry;
        final KeyStore.ProtectionParameter protectionParam;
        
        
        decodedByteArray = loadPasscode(pSvcPair);
        
        try {
            
            protectionParam = new KeyStore
                    .PasswordProtection(pAccID.toCharArray());
            
            keyEntry = pwStore.getEntry(pSvcPair, protectionParam);
            
            boolean checkEntry = pwStore
                    .entryInstanceOf(pSvcPair, KeyStore.SecretKeyEntry.class);
            
            if(!checkEntry) {
                return null;
            }
            
            secretKeyEntry = (KeyStore.SecretKeyEntry)keyEntry;
            theSecretKey = secretKeyEntry.getSecretKey();
            
            decryptedByteArray = pwCodec.activateDecryption(theSecretKey, decodedByteArray);
            
            return new String(decryptedByteArray, Charset.forName("UTF-8"));
            
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }
    
    
    
    public final boolean isThis(final String pSvcPair) {
        
        try {
            System.out.println(pwStore.isKeyEntry(pSvcPair));
            
            return pwStore.isKeyEntry(pSvcPair);
            
        } catch (KeyStoreException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public final void enumerateKeys() {
        
        try {
            Enumeration<String> a = pwStore.aliases();
            
            while(a.hasMoreElements()) {
                System.out.println(a.nextElement());
            }
            
        } catch (KeyStoreException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
