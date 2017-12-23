package com.adribast.clavarnak.sender_receiver;

import com.adribast.clavarnak.ui.SendUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPMessageReceiverService implements MessageReceiverService, Runnable {


    private SendUI send;

    //handler of received messages
    private IncomingMessageListener incomingMessageListener;

    //connection sockets
    private ServerSocket serverSocket;

    //allows reading the content of buffer
    private BufferedReader reader;


    //These booleans' roles is to inform when initialising sockets and when to stop listening
    private static boolean connectionInitialized;
    private static boolean connectionEnded;

    //Is used for MasterListener in order to not stop listening after a connection closing
    private static boolean multipleListen;

    private static int ourPort;

    public TCPMessageReceiverService(IncomingMessageListener ourIncomingMessageListener,int ourPort,
                                     SendUI send) throws IOException {
        this.send = send;
        this.incomingMessageListener=ourIncomingMessageListener;

        this.connectionInitialized=false;
        this.connectionEnded = false;
        this.multipleListen=false;

        this.ourPort = ourPort;
        this.serverSocket = new ServerSocket(ourPort);
    }

    public TCPMessageReceiverService(IncomingMessageListener ourIncomingMessageListener,int ourPort) throws IOException {
        this.incomingMessageListener=ourIncomingMessageListener;

        this.connectionInitialized=false;
        this.connectionEnded = false;
        this.multipleListen=false;

        System.out.println("BIND ON PORT : " +ourPort+"\n");
        this.serverSocket = new ServerSocket(ourPort);
        this.ourPort = ourPort;
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
        if (message != null) {
            if (message.toUpperCase().compareTo("CLOSE CONNECTION") == 0) {
                this.connectionEnded = true;
                System.out.println("Connexion ended \n");
                send.onTCP("connection ended");
                TCPMessageSenderService serviceSend =
                        (TCPMessageSenderService) send.getServiceFactory().onTCP();
                serviceSend.endConnection();
                this.incomingMessageListener.onNewIncomingMessage(message);


            }

            else if (message.toUpperCase().compareTo("CONNECTION ENDED") == 0) {
                this.connectionEnded = true;
                System.out.println("Connexion really ended \n");
            }

            else {
                this.incomingMessageListener.onNewIncomingMessage(message);
            }
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
        System.out.println("Initialising connexion on port :"+this.ourPort +"\n");
        Socket chatSocket = serverSocket.accept();

        InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
        this.reader = new BufferedReader(stream);
        this.connectionInitialized = true;
        System.out.println("Connexion Initialised\n");
    }

    @Override
    public void run() {

        try {
            while (!this.connectionEnded) {
                listen();
            }

            this.endConnection();

        } catch (Exception e) {

            e.printStackTrace();

        }

        while (multipleListen) {
            try {
                System.out.println("PAS NORMAL");
                this.serverSocket = new ServerSocket(this.ourPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.connectionEnded = false;
            this.connectionInitialized=false;
            try {
                while (!this.connectionEnded) {
                    listen();
                }
                this.endConnection();

            } catch (Exception e) {

                e.printStackTrace();

            }
        }

    }

    public void setConnectionEnded(boolean isEnded){
        this.connectionEnded = isEnded;
    }
    public void setMultipleListen(){
        this.multipleListen = true;
    }

}
