package com.adribast.clavarnak.sender_receiver;


import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPMessageReceiverService implements MessageReceiverService {
    int port;
    IncomingMessageListener incomingMessageListener;

    public UDPMessageReceiverService(IncomingMessageListener ourIncomingMessageListener,int ourPort){
        this.port=ourPort;
        this.incomingMessageListener=ourIncomingMessageListener;
    }

    @Override
    public void listenOnPort(int port, IncomingMessageListener incomingMessageListener) throws Exception {
        DatagramSocket receiverSocket = new DatagramSocket(port);
        DatagramPacket receivedPacket = new DatagramPacket(new byte[500], 500);
        receiverSocket.receive(receivedPacket);
        byte[] data = receivedPacket.getData();

        receiverSocket.close();

        incomingMessageListener.onNewIncomingMessage(new String(data));
    }

    @Override
    public void run() {
        try {
            listenOnPort(this.port, this.incomingMessageListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
