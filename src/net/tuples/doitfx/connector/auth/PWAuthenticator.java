/*
 * Copyright (C) 2014 ideatuples
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

package net.tuples.doitfx.connector.auth;

import javax.mail.PasswordAuthentication;

/**
 * PWAuthenticator is a class for KeyStore object which stores Key value objects of
 * already configured.
 * 
 * In Java Cryptography Extension(JCE), it uses KeyStore object to store keys for 
 * ciphertext, so we need to create this object for getting password to decrypt ciphertext
 * to plaintext.
 * 
 * @version 0.0.1, 26 Dec 2014
 * @see KeyStore
 * 
 */

public final class PWAuthenticator extends javax.mail.Authenticator {

    private final String username;
    private final String password; 
    
    /**
     * 
     * @param pUsername Username for an account.
     * @param pPassword Password for an account.
     */
    public PWAuthenticator(final String pUsername, final String pPassword) {
        this.username = pUsername;
        this.password = pPassword;
    }
    
    /**
     * Getting PasswordAuthentication object to release KeyStore.
     * 
     * @return PasswordAuthention object.
     */

    @Override
    public final PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication(username, password);
    }

}
