package com.adribast.clavarnak.sender_receiver;


import com.adribast.clavarnak.UsersManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import static com.adribast.clavarnak.Main.myAlias;

public class UDPMessageReceiverService implements Runnable {
    private int port;
    private UsersManager UM ;
    private DatagramSocket receiverSocket;

    //UM sert dans le cas de la réception d'un pseudo à ajouter dans la liste des utilisateurs
    public UDPMessageReceiverService(int ourPort, UsersManager UM) throws SocketException {
        this.port=ourPort;
        this.UM=UM;
        this.receiverSocket = new DatagramSocket(this.port);

    }

    private void listen() throws Exception {

        DatagramPacket receivedPacket = new DatagramPacket(new byte[500], 500);
        this.receiverSocket.receive(receivedPacket);
        String data = new String(receivedPacket.getData());
        Scanner s = new Scanner(data);

        String sourceIP = receivedPacket.getAddress().toString();

        sourceIP = sourceIP.substring(1);

        //si le paquet est une demande de pseudo, on répond avec notre pseudo
        if (data.toUpperCase().contains("PLEASE SEND YOUR ALIAS")) {

            System.out.println("Alias request \n");
            UDPMessageSenderService aliasSender;
            aliasSender = new UDPMessageSenderService(this.port, sourceIP);
            System.out.println("PSEUDO :" + myAlias);
            aliasSender.sendMessageOn(myAlias);
        }

        //si le paquet est un pseudo (1 seul mot) qui n'est pas le notre
        if (data.toUpperCase().contains("PSEUDO :")) {
            s.useDelimiter(":");
            s.next();
            String newAlias = s.next();

            if (newAlias.compareTo(myAlias)!=0) {
                UM.addUser(newAlias, sourceIP);
                System.out.println("ALIAS " + newAlias);
            }
        }

        //si le paquet est une notification de deconnexion, on supprime l'entree de la liste des utilisateurs
        if (data.toUpperCase().contains("DISCONNECTED USER :")) {
            s.useDelimiter(":");
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
