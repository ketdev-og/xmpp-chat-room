package com.ketem.vysper;





import org.apache.vysper.mina.TCPEndpoint;
import org.apache.vysper.storage.StorageProviderRegistry;
import org.apache.vysper.storage.inmemory.MemoryStorageProviderRegistry;
import org.apache.vysper.xmpp.addressing.Entity;
import org.apache.vysper.xmpp.addressing.EntityFormatException;
import org.apache.vysper.xmpp.addressing.EntityImpl;
import org.apache.vysper.xmpp.authorization.AccountCreationException;
import org.apache.vysper.xmpp.authorization.AccountManagement;
import org.apache.vysper.xmpp.modules.extension.xep0049_privatedata.PrivateDataModule;
import org.apache.vysper.xmpp.modules.extension.xep0054_vcardtemp.VcardTempModule;
import org.apache.vysper.xmpp.modules.extension.xep0092_software_version.SoftwareVersionModule;
import org.apache.vysper.xmpp.modules.extension.xep0119_xmppping.XmppPingModule;
import org.apache.vysper.xmpp.modules.extension.xep0202_entity_time.EntityTimeModule;
import org.apache.vysper.xmpp.server.XMPPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
;import java.io.File;
import java.io.FileNotFoundException;

@SpringBootApplication
public class VysperApplication {

    private static String serverIsRunning;

    public static void main(String[] args) throws AccountCreationException, EntityFormatException, FileNotFoundException {
     SpringApplication.run(VysperApplication.class, args);
        // choose the storage you want to use
        //StorageProviderRegistry providerRegistry = new JcrStorageProviderRegistry();
        StorageProviderRegistry providerRegistry = new MemoryStorageProviderRegistry();

        final AccountManagement accountManagement = (AccountManagement) providerRegistry
                .retrieve(AccountManagement.class);

        Entity user1 = EntityImpl.parse("user1@vysper.org");
        if (!accountManagement.verifyAccountExists(user1)) {
            accountManagement.addUser(user1, "password1");
        }
        Entity user2 = EntityImpl.parse("user2@vysper.org");
        if (!accountManagement.verifyAccountExists(user2)) {
            accountManagement.addUser(user2, "password1");
        }
        Entity user3 = EntityImpl.parse("user3@vysper.org");
        if (!accountManagement.verifyAccountExists(user3)) {
            accountManagement.addUser(user3, "password1");
        }


        XMPPServer server = new XMPPServer("vysper.org");

        server.setStorageProviderRegistry(providerRegistry);
        server.addEndpoint(new TCPEndpoint());

        server.setTLSCertificateInfo(new File("src/main/resources/bogus_mina_tls.cert"), "boguspw");

        try {
            server.start();
            System.out.println("vysper server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        server.addModule(new SoftwareVersionModule());
        server.addModule(new EntityTimeModule());
        server.addModule(new VcardTempModule());
        server.addModule(new XmppPingModule());
        server.addModule(new PrivateDataModule());

        
    }


}

