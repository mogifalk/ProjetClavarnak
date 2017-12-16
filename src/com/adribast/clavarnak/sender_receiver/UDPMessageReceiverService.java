package com.adribast.clavarnak.sender_receiver;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static com.adribast.clavarnak.Main.broadcastPort;

public class UDPMessageReceiverService implements MessageReceiverService {
    int port;
    IncomingMessageListener incomingMessageListener;

    public UDPMessageReceiverService(IncomingMessageListener ourIncomingMessageListener,int ourPort){
        this.port=ourPort;
        this.incomingMessageListener=ourIncomingMessageListener;
    }

    @Override
    public void listen() throws Exception {
        DatagramSocket receiverSocket = new DatagramSocket(this.port);
        DatagramPacket receivedPacket = new DatagramPacket(new byte[500], 500);
        receiverSocket.receive(receivedPacket);
        String data = new String(receivedPacket.getData());

        receiverSocket.close();
        if (receivedPacket.getPort()==broadcastPort) {
            new UDPBroadcastReceiverService(receivedPacket); //CEST LA QUE T'AS ARRETE
        }

        this.incomingMessageListener.onNewIncomingMessage(new String(data));
    }

    @Override
    public void endConnection() throws IOException {
        try {
            listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
