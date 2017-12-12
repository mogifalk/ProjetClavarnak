package com.adribast.clavarnak.sender_receiver;

import java.net.*;
import java.util.Scanner;

import static com.adribast.clavarnak.Main.broadcastPort;

public class UDPMessageSenderService implements MessageSenderService {
    @Override
    public void sendMessageOn(String ipAddress, int port, String message) throws Exception {
        DatagramSocket senderSocket = new DatagramSocket();
        byte[] data = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
        datagramPacket.setAddress(InetAddress.getByName(ipAddress));
        datagramPacket.setPort(port);

        senderSocket.send(datagramPacket);

        senderSocket.close();
    }

    public void sendInitBroadcast () throws Exception {

        try {
            String broadcastAdr = "127.255.255.255";
            sendMessageOn(broadcastAdr, broadcastPort, "Please send me your IP and pseudo");
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
