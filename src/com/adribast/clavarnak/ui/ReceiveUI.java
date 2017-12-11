package com.adribast.clavarnak.ui;

import com.adribast.clavarnak.ChatWindow;
import com.adribast.clavarnak.sender_receiver.IncomingMessageListener;
import com.adribast.clavarnak.sender_receiver.MessageReceiverService;
import com.adribast.clavarnak.sender_receiver.factory.MessageReceiverServiceFactory;

import javax.swing.*;
import java.util.ArrayList;

public class ReceiveUI implements CommunicationUI, IncomingMessageListener {
    private static final String ERROR_MESSAGE = "[ERROR] An error occured while trying to listen on port";

    private final MessageReceiverServiceFactory messageReceiverServiceFactory;
    private int port;
    private String conv ="";

    public ReceiveUI(MessageReceiverServiceFactory messageReceiverServiceFactory, int ourport) {
        this.messageReceiverServiceFactory = messageReceiverServiceFactory;
        this.port = ourport;
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
        this.conv = message;
    }

    private void askForPort(MessageReceiverService messageReceiverService) {
        System.out.print("Enter the port to listen on: " + this.port);
        int port = this.port;
        try {
            messageReceiverService.listenOnPort(port, this);
        } catch (Exception exception) {
            System.err.println(ERROR_MESSAGE);
            System.err.println(exception);
        }
    }

    public String getConversation(){
        return this.conv;
    }

}
