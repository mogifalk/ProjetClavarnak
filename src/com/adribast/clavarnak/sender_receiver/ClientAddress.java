package com.adribast.clavarnak.sender_receiver;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientAddress {

    public String getLocalAddress() {

        String localAddress=null;

        try {
            InetAddress inetadr;
            inetadr = InetAddress.getLocalHost();
            //adresse ip sur le réseau
            localAddress = inetadr.toString();
            return localAddress;

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
