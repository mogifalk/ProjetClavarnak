package com.adribast.clavarnak.sender_receiver;


import com.adribast.clavarnak.UsersManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import static com.adribast.clavarnak.Main.configPort;
import static com.adribast.clavarnak.Main.myAlias;

public class UDPMessageReceiverService implements Runnable {
    private int port;
    private UsersManager UM ;

    //UM sert dans le cas de la réception d'un pseudo à ajouter dans la liste des utilisateurs
    public UDPMessageReceiverService(int ourPort, UsersManager UM){
        this.port=ourPort;
        this.UM=UM;
    }

    private void listen() throws Exception {
        DatagramSocket receiverSocket = new DatagramSocket(this.port);
        DatagramPacket receivedPacket = new DatagramPacket(new byte[500], 500);
        receiverSocket.receive(receivedPacket);
        String data = new String(receivedPacket.getData());
        Scanner s = new Scanner(data);

        String sourceIP = receivedPacket.getAddress().toString();
        String myIP = InetAddress.getLocalHost().getHostAddress();
        int sourcePort = receivedPacket.getPort();

        receiverSocket.close();

        //si le paquet est une demande de pseudo, on répond avec notre pseudo
        if (receivedPacket.getPort()==configPort &&
                data.toUpperCase().compareTo("PLEASE SEND YOUR ALIAS")==0) {

            UDPMessageSenderService aliasSender;
            aliasSender = new UDPMessageSenderService(sourcePort, sourceIP);
            aliasSender.sendMessageOn(myAlias);
        }

        //si le paquet est un pseudo (1 seul mot) qui n'est pas le notre
        if (data.split(" ").length==1 && !data.contains(myAlias)) {
            s.useDelimiter(" ");
            String newAlias = s.next();

            UM.addUser(newAlias,sourceIP);
            System.out.println("ALIAS " + newAlias);
        }

        //si le paquet est une notification de deconnexion, on supprime l'entree de la liste des utilisateurs
        if (data.toUpperCase().contains("DISCONNECTED USER : ")) {
            s.useDelimiter(": ");
            s.next();

            String disconnectedAlias = s.next() ;
            this.UM.delUser(disconnectedAlias);
        }
    }

    public void run() {
        /*le while(true) est justifié car UDP ne nous sert que pour les messages de config comme les broadcasts,
         les envois de pseudos... que nous écoutons durant toute la durée de vie de l'application*/

        try {
            while (true) {

            listen();
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
