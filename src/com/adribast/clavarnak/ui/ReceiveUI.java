package com.adribast.clavarnak.ui;

import com.adribast.clavarnak.ChatWindow;
import com.adribast.clavarnak.sender_receiver.IncomingMessageListener;
import com.adribast.clavarnak.sender_receiver.MessageReceiverService;
import com.adribast.clavarnak.sender_receiver.factory.MessageReceiverServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReceiveUI implements CommunicationUI, IncomingMessageListener {
    private static final String ERROR_MESSAGE = "[ERROR] An error occured while trying to listen on port";

    public final MessageReceiverServiceFactory messageReceiverServiceFactory;
    private int port;
    private ChatWindow chat;

    public ReceiveUI(int ourPort, ChatWindow chat) {
        this.messageReceiverServiceFactory = new MessageReceiverServiceFactory(this,ourPort);
        this.port = ourPort;
        this.chat = chat;
    }

    @Override
    public void onUDP(String mess) {
        askForPort(messageReceiverServiceFactory.onUDP());
    }

    @Override
    public void onTCP(String mess) {
        askForPort(messageReceiverServiceFactory.onTCP());
    }

    @Override
    public void onNewIncomingMessage(String message) {
        System.out.println("New incoming message: " + message);


        System.out.println("NEW MESSAGE : "+message);
        ArrayList<JLabel> conv = new ArrayList<>();

        this.chat.addWithReturn(message,conv);
        this.chat.printConversation(conv, BorderLayout.WEST);
    }

    private void askForPort(MessageReceiverService messageReceiverService) {
        System.out.print("Enter the port to listen on: " + this.port);
        try {
            Thread listenThread = new Thread(messageReceiverService);
            listenThread.start();
        } catch (Exception exception) {
            System.err.println(ERROR_MESSAGE);
            System.err.println(exception);
        }
    }


}
