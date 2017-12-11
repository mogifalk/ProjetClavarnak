package com.adribast.clavarnak.sender_receiver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPMessageReceiverService implements MessageReceiverService, Runnable {

    int port;
    IncomingMessageListener incomingMessageListener;

    public TCPMessageReceiverService(IncomingMessageListener ourIncomingMessageListener,int ourPort){
        this.port=ourPort;
        this.incomingMessageListener=ourIncomingMessageListener;
    }

    @Override
    public void listenOnPort(int port, IncomingMessageListener incomingMessageListener) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket chatSocket = serverSocket.accept();
        InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
        BufferedReader reader = new BufferedReader(stream);

        String message = reader.readLine();
        incomingMessageListener.onNewIncomingMessage(message);

        reader.close();
        serverSocket.close();

    }

    @Override
    public void run() {

        try {
            listenOnPort(this.port, this.incomingMessageListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
