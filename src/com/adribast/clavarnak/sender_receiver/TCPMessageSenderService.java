package com.adribast.clavarnak.sender_receiver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPMessageSenderService implements MessageSenderService {

    //on envoie les messages sur ce port et cette adresse
    private int port;
    private String ipAddress;

    //socket d'envoie
    private Socket chatSocket;

    //pour écrire dans la socket
    private PrintWriter writer;

    //permet de savoir si c'est le premier message qui est envoyé et si on doit
    //initialiser la socket
    private boolean connectionInitialised;


    public TCPMessageSenderService(int p, String ip) {
        this.connectionInitialised = false;
        this.port = p;
        this.ipAddress = ip;
    }

    //Cette fonction envoie le message sur le port parametre de la classe
    @Override
    public void sendMessageOn(String message) throws IOException {

        if (!this.connectionInitialised){
            this.initialiseConnection();
        }
        this.writer.println(message);
        this.writer.flush();
    }

    //procedure de fin de connection appelée dans windowListener
    public void endConnection() throws IOException {

            // on envoie un message qui annonce que les sockets vont se fermer
            this.sendMessageOn("close connection");

            this.writer.close();
            this.chatSocket.close();
            System.out.println("Send connection ended\n");
    }

    public void freeConnection() throws IOException {

            // on envoie un message qui annonce que les sockets vont se fermer
            this.sendMessageOn("free connection");

            this.writer.close();
            this.chatSocket.close();
            System.out.println("Send connection free\n");
    }

    private void initialiseConnection() throws IOException {
        this.chatSocket = new Socket(this.ipAddress, this.port);
        this.writer = new PrintWriter(chatSocket.getOutputStream());
        this.connectionInitialised = true;

        System.out.println("\nConnection initialised on port : " + this.port + "\n");
    }
}
