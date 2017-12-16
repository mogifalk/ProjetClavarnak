package com.adribast.clavarnak.sender_receiver;

import com.adribast.clavarnak.ui.SendUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPMessageReceiverService implements MessageReceiverService, Runnable {

    private int port;
    private SendUI send;

    //permet de recuperer le message reçu et de le donner a received UI qui l'affiche
    private IncomingMessageListener incomingMessageListener;

    //sockets de connexion
    private static ServerSocket serverSocket;
    private Socket chatSocket;

    //permet de recuperer le contenu des messages
    private BufferedReader reader;


    //Ces deux booleens nous permettent respectivement de savoir quand initialiser les sockets
    // et quand arreter d'écouter
    private boolean connectionInitialized;
    private boolean connectionEnded;

    public TCPMessageReceiverService(IncomingMessageListener ourIncomingMessageListener,int ourPort,
                                     SendUI send) throws IOException {
        this.port=ourPort;
        this.send = send;
        this.incomingMessageListener=ourIncomingMessageListener;

        this.connectionInitialized=false;
        this.connectionEnded = false;

        System.out.println("BIND\n");
        this.serverSocket = new ServerSocket(this.port);
    }

    //Cette fonction écoute sur le port attribut de la classe renvoie les messages reçus
    //au incomingMessageListener
    //Si le message reçu est une fin de connection elle arrête d'écouter sur le port
    @Override
    public void listen() throws Exception {

        if (!this.connectionInitialized){
            this.initializeConnection();
        }

        String message = reader.readLine();

        if (message.toUpperCase().compareTo("CLOSE CONNECTION")==0) {
            this.connectionEnded = true;
            System.out.println("Connexion ended \n");
            send.onTCP("connection ended");
        }
        else if (message.toUpperCase().compareTo("CONNECTION ENDED")==0){
            this.connectionEnded = true;
            System.out.println("Connexion really ended \n");
        }
        else {
            this.incomingMessageListener.onNewIncomingMessage(message);
        }

    }

    //fonction de fin de connection appelée par le window listener
    public void endConnection() throws IOException {
        if (this.reader!=null){
            this.reader.close();
            this.serverSocket.close();
            System.out.println("Listen connection ended\n");
        }
        else{
            this.serverSocket.close();
            System.out.println("Listen connection ended\n");
        }

        this.connectionEnded=true;
    }


    private void initializeConnection() throws IOException {
        this.chatSocket = serverSocket.accept();

        InputStreamReader stream = new InputStreamReader(this.chatSocket.getInputStream());
        this.reader = new BufferedReader(stream);
        this.connectionInitialized = true;
    }

    @Override
    public void run() {


            try {
                while(!this.connectionEnded) {
                    listen();
                }
                this.endConnection();

            } catch (Exception e) {

                e.printStackTrace();

            }

    }

    public void setConnectionEnded(boolean connectionEnded) {
        this.connectionEnded = connectionEnded;
    }

    public int getPort(){
        return this.port;
    }
}
