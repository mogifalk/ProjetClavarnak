package com.adribast.clavarnak.sender_receiver;

import java.net.*;
import java.util.Scanner;

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

    public void sendInitBroadcast () {
        broadcastAddressBuilder();
    }

    public String broadcastAddressBuilder () {
        String localAddress = new ClientAddress().getLocalAddress();
        System.out.println(localAddress);
        Scanner s = new Scanner(localAddress);
        s.useDelimiter(".");

        String broadcastAdress="";
        broadcastAdress.concat(s.next()+"."); //1er octet de l'IP locale
        broadcastAdress.concat(s.next()+"."); //2e *****
        broadcastAdress.concat(s.next()+"."); //3e *****
        broadcastAdress.concat("255") ;

        return broadcastAdress ;
    }
}
