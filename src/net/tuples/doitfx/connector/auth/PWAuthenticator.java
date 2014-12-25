/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tuples.doitfx.connector.auth;

/**
 *
 * @author ideatuples
 */

public final class PWAuthenticator extends javax.mail.Authenticator {

    private final String username;
    private final String password; 

    public PWAuthenticator(final String pUsername, final String pPassword) {
        this.username = pUsername;
        this.password = pPassword;
    }

    @Override
    public javax.mail.PasswordAuthentication getPasswordAuthentication() {

        return new javax.mail.PasswordAuthentication(username, password);
    }

}
