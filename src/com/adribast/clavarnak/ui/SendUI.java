package com.adribast.clavarnak.ui;

import com.adribast.clavarnak.sender_receiver.MessageSenderService;
import com.adribast.clavarnak.sender_receiver.factory.MessageSenderServiceFactory;

import javax.swing.*;
import java.util.ArrayList;

public class SendUI implements CommunicationUI {

    private static final String ERROR_MESSAGE = "[ERROR] An error occured while trying to send message";
    private static final String NOTIFICATION_FORMAT = "[INFO] Your message has been sent to %s on port %d";

    private final MessageSenderServiceFactory messageSenderServiceFactory;
    private int port;
    private String ipAddr;


    public SendUI(MessageSenderServiceFactory messageSenderServiceFactory, String ip, int ourport) {
        this.messageSenderServiceFactory = messageSenderServiceFactory;
        this.port = ourport;
        this.ipAddr = ip;
    }

    @Override
    public void onTCP(String mess) {
        sendMessageWith(messageSenderServiceFactory.onTCP(), mess);
    }

    @Override
    public void onUDP(String mess) {
        sendMessageWith(messageSenderServiceFactory.onUDP(),mess);
    }

    private void sendMessageWith(MessageSenderService messageSenderService, String mess) {
        System.out.print("Destination IP address : " + this.ipAddr);
        String ipAddress = this.ipAddr;
        System.out.print("Destination port : "+ this.port);
        int port = this.port;
        System.out.print("Message : "+mess);
        String message = mess;
        try {
            messageSenderService.sendMessageOn(ipAddress, port, message);
            System.out.println(String.format(NOTIFICATION_FORMAT, ipAddress, port));
        } catch (Exception exception) {
            System.err.println(ERROR_MESSAGE);
            System.err.println(exception);
        }
    }
}
