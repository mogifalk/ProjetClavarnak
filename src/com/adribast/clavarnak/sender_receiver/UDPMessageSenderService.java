package com.adribast.clavarnak.sender_receiver;

import java.io.IOException;
import java.net.*;

public class UDPMessageSenderService implements MessageSenderService {

    private int port;
    private String ipAddress;

    public UDPMessageSenderService(int p,String ip){
        this.port = p;
        this.ipAddress = ip;
    }
    @Override
    public void sendMessageOn(String message) throws Exception {

        DatagramSocket senderSocket = new DatagramSocket();
        byte[] data = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
        System.out.println(this.ipAddress);
        datagramPacket.setAddress(InetAddress.getByName(this.ipAddress));
        datagramPacket.setPort(this.port);

        senderSocket.send(datagramPacket);

        senderSocket.close();
    }

    @Override
    public void endConnection() throws IOException {

    }

    /*public void sendInitBroadcast () throws Exception {

        try {
            String broadcastAdr = "127.255.255.255";
            sendMessageOn(broadcastAdr, "Please send me your IP and pseudo");
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }*/
}
