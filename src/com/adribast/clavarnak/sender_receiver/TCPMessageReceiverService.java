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

        connectionInitialized=false;
        connectionEnded = false;
        multipleListen=false;

        TCPMessageReceiverService.ourPort = ourPort;
        this.serverSocket = new ServerSocket(ourPort);
    }

    public TCPMessageReceiverService(IncomingMessageListener ourIncomingMessageListener,int ourPort) throws IOException {
        this.incomingMessageListener=ourIncomingMessageListener;

        connectionInitialized=false;
        connectionEnded = false;
        multipleListen=false;

        System.out.println("BIND ON PORT : " +ourPort+"\n");
        this.serverSocket = new ServerSocket(ourPort);
        TCPMessageReceiverService.ourPort = ourPort;
    }

    //Cette fonction écoute sur le port attribut de la classe renvoie les messages reçus
    //au incomingMessageListener
    //Si le message reçu est une fin de connection elle arrête d'écouter sur le port
    @Override
    public void listen() throws Exception {

        if (!connectionInitialized){
            this.initializeConnection();
        }

        String message = reader.readLine();
        if (message != null) {
            if (message.toUpperCase().compareTo("CLOSE CONNECTION") == 0) {
                connectionEnded = true;
                System.out.println("Connection ended \n");
                send.onTCP("Connection ended");

                TCPMessageSenderService serviceSend;
                serviceSend = (TCPMessageSenderService) send.getServiceFactory().onTCP();
                serviceSend.endConnection();
                this.incomingMessageListener.onNewIncomingMessage(message);


            }

            else if (message.toUpperCase().compareTo("CONNECTION ENDED") == 0) {
                connectionEnded = true;
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

        connectionEnded=true;
    }


    private void initializeConnection() throws IOException {
        System.out.println("Initialising connexion on port :"+ ourPort +"\n");
        Socket chatSocket = serverSocket.accept();

        InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
        this.reader = new BufferedReader(stream);
        connectionInitialized = true;
        System.out.println("Connexion Initialised\n");
    }

    @Override
    public void run() {

        try {
            while (!connectionEnded) {
                listen();
            }

            this.endConnection();

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        while (multipleListen) {
            try {
                System.out.println("PAS NORMAL");
                this.serverSocket = new ServerSocket(ourPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
            connectionEnded = false;
            connectionInitialized=false;
            try {
                while (!connectionEnded) {
                    listen();
                }
                this.endConnection();

            } catch (Exception e) {

                e.printStackTrace();

            }
        }

    }

    public void setConnectionEnded(boolean isEnded){
        connectionEnded = isEnded;
    }
    public void setMultipleListen(){
        multipleListen = true;
    }

}
