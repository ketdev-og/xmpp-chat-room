package com.ketem.vysper;

import org.apache.vysper.mina.TCPEndpoint;
import org.apache.vysper.storage.StorageProviderRegistry;
import org.apache.vysper.storage.inmemory.MemoryStorageProviderRegistry;
import org.apache.vysper.storage.jcr.JcrStorageProviderRegistry;
import org.apache.vysper.xmpp.addressing.Entity;
import org.apache.vysper.xmpp.addressing.EntityFormatException;
import org.apache.vysper.xmpp.addressing.EntityImpl;
import org.apache.vysper.xmpp.authorization.AccountCreationException;
import org.apache.vysper.xmpp.authorization.AccountManagement;
import org.apache.vysper.xmpp.authorization.UserAuthorization;
import org.apache.vysper.xmpp.server.XMPPServer;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class Server  implements UserAuthorization {

    @Override
    public boolean verifyCredentials(Entity jid, String passwordCleartext, Object credentials) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:389/");

        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        // extract the user name from the entity, e.g. from foo@example.com, foo will be used
        env.put(Context.SECURITY_PRINCIPAL, String.format("cn=%s, ou=Users, o=Acme", jid.getNode()));
        env.put(Context.SECURITY_CREDENTIALS, passwordCleartext);

        try {
            // connect and authenticate with the directory
            new InitialDirContext(env);
            return true;
        } catch (NamingException e) {
            return false;
        }
    }

    @Override
    public boolean verifyCredentials(String s, String s1, Object o) {
        return false;
    }


}
