package com.adribast.clavarnak.sender_receiver;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static com.adribast.clavarnak.Main.UM;
import static com.adribast.clavarnak.Main.broadcastPort;
import static com.adribast.clavarnak.Main.myAlias;

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
        String data = new String(receivedPacket.getData());

        receiverSocket.close();
        if (receivedPacket.getPort()==broadcastPort) {

            //si notre alias est demandé par broadcast, on répond en UDP à l'émetteur
            if (data.compareTo("Please send your alias")==0) {
                UDPMessageSenderService sender;
                sender = new UDPMessageSenderService(broadcastPort,receivedPacket.getAddress().toString());
                sender.sendMessageOn(myAlias);
            }

            /*si la longueur du message est de 1 mot, on considère qu'on a reçu un pseudo
            dans ce cas on actualise la liste d'utilisateurs*/
            else if (data.split(" ").length==1) {
                UM.addUser(data,receivedPacket.getAddress().toString());
            }
        }

        incomingMessageListener.onNewIncomingMessage(new String(data));
    }

    @Override
    public void endConnection() throws IOException {
        try {
            listenOnPort(this.port, this.incomingMessageListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
