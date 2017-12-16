package com.adribast.clavarnak.sender_receiver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPMessageSenderService implements MessageSenderService {

    private Socket chatSocket;
    private PrintWriter writer;
    private boolean connectionInitialised;
    private int port;
    private String ipAddress;

    public TCPMessageSenderService(int p, String ip) {
        this.connectionInitialised = false;
        this.port = p;
        this.ipAddress = ip;

    }

    @Override
    public void sendMessageOn(String message) throws IOException {

        if (!this.connectionInitialised){
            this.initialiseConnection();
        }
        this.writer.println(message);
        this.writer.flush();
    }

    public void endConnection() throws IOException {
        if (this.writer!=null & this.chatSocket != null){
            this.writer.close();
            this.chatSocket.close();
        }
    }

    private void initialiseConnection() throws IOException {
        this.chatSocket = new Socket(this.ipAddress, this.port);
        this.writer = new PrintWriter(chatSocket.getOutputStream());
        this.connectionInitialised = true;
        System.out.println("\nConnection initialised on port : " + this.port);
    }
}
