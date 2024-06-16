package com.ketem.vysper;

import org.apache.vysper.mina.S2SEndpoint;
import org.apache.vysper.xmpp.server.XMPPServer;
import org.apache.vysper.xmpp.server.s2s.XMPPServerConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.http.WebSocket;

@SpringBootApplication
public class VysperApplication {

    public static void main(String[] args) {
        SpringApplication.run(VysperApplication.class, args);
    }

    public void testIt() throws Exception {
        XMPPServer xmppServer =  new XMPPServer("vysper.org");
        xmppServer.addEndpoint(new S2SEndpoint());
        xmppServer.start();
        xmppServer.getServerRuntimeContext().getServerFeatures().setRelayingToFederationServers(true);




    }

}
