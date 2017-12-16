package com.adribast.clavarnak.sender_receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPMessageReceiverService implements MessageReceiverService, Runnable {

    int port;
    private IncomingMessageListener incomingMessageListener;
    private ServerSocket serverSocket;
    private BufferedReader reader;
    private Socket chatSocket;
    private boolean connectionInitialized;
    private boolean connectionEnded;

    public TCPMessageReceiverService(IncomingMessageListener ourIncomingMessageListener,int ourPort){
        this.port=ourPort;
        this.incomingMessageListener=ourIncomingMessageListener;
        this.connectionInitialized=false;
        this.connectionEnded = false;
    }

    @Override
    public void listenOnPort(int port, IncomingMessageListener incomingMessageListener) throws Exception {

        if (!this.connectionInitialized){
            this.initializeConnection();
        }

        String message = reader.readLine();

        if (message.toUpperCase().equalsIgnoreCase("CLOSE CONNECTION")){
            this.connectionEnded = true;
            System.out.println("\n connexion ended \n");
        }
        else {
            incomingMessageListener.onNewIncomingMessage(message);
        }

    }

    @Override
    public void run() {


            try {
                while(!this.connectionEnded) {
                    listenOnPort(this.port, this.incomingMessageListener);
                }

            } catch (Exception e) {

                e.printStackTrace();

            }

    }

    public void endConnection() throws IOException {
        if (this.reader!=null & this.serverSocket != null){
            this.reader.close();
            this.serverSocket.close();
        }
    }

    private void initializeConnection() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        this.chatSocket = serverSocket.accept();
        InputStreamReader stream = new InputStreamReader(this.chatSocket.getInputStream());

        this.reader = new BufferedReader(stream);
        this.connectionInitialized = true;
    }
}
