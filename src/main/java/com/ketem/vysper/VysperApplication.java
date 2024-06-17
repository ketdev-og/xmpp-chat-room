package com.ketem.vysper;


import org.apache.vysper.mina.TCPEndpoint;
import org.apache.vysper.storage.OpenStorageProviderRegistry;
import org.apache.vysper.storage.StorageProvider;
import org.apache.vysper.storage.StorageProviderRegistry;
import org.apache.vysper.storage.inmemory.MemoryStorageProviderRegistry;
import org.apache.vysper.storage.jcr.JcrStorageProviderRegistry;
import org.apache.vysper.xmpp.addressing.Entity;
import org.apache.vysper.xmpp.authorization.UserAuthorization;
import org.apache.vysper.xmpp.modules.roster.persistence.MemoryRosterManager;
import org.apache.vysper.xmpp.server.XMPPServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.net.http.WebSocket;
import java.util.Hashtable;

@SpringBootApplication
public class VysperApplication {

    public static void main(String[] args) throws Exception {
        OpenStorageProviderRegistry providerRegistry = new OpenStorageProviderRegistry();

        // add your custom authentication provider
        providerRegistry.add(new MyLdapAuthentication());

        // a roster manager is also required
        providerRegistry.add(new MemoryRosterManager());

        XMPPServer server = new XMPPServer("acme.com");

        // other initialization
        // ...

        server.setStorageProviderRegistry(providerRegistry);

        server.start();
SpringApplication.run(VysperApplication.class, args);

    }


}

