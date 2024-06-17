package com.ketem.vysper;

import org.apache.vysper.storage.OpenStorageProviderRegistry;
import org.apache.vysper.xmpp.modules.roster.persistence.MemoryRosterManager;
import org.apache.vysper.xmpp.server.XMPPServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmppTest{
    

    public void testIt() throws Exception {
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


    }
}
