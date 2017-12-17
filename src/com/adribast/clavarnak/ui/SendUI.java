package com.adribast.clavarnak.ui;

import com.adribast.clavarnak.sender_receiver.MessageSenderService;
import com.adribast.clavarnak.sender_receiver.TCPMessageSenderService;
import com.adribast.clavarnak.sender_receiver.factory.MessageSenderServiceFactory;
import com.adribast.clavarnak.sender_receiver.factory.MessageServiceFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class SendUI implements CommunicationUI {

    private static final String ERROR_MESSAGE = "[ERROR] An error occured while trying to send message\n";
    private static final String NOTIFICATION_FORMAT = "[INFO] Your message has been sent to %s on port %d\n";

    private final MessageSenderServiceFactory messageSenderServiceFactory;
    private int port;

    //adresse ip a laquelle les messages seront envoyés
    private String ipAddr;


    public SendUI(String ip, int ourport) throws IOException {
        this.messageSenderServiceFactory = new MessageSenderServiceFactory(ourport,ip);
        this.port = ourport;
        this.ipAddr = ip;
    }

    @Override
    public void onTCP(String mess){
        sendMessageWith(messageSenderServiceFactory.onTCP(), mess);
    }

    @Override
    public void onUDP(String mess) {
        sendMessageWith(messageSenderServiceFactory.onUDP(),mess);
    }

    //On doit pourvoir le recuperer pour fermer les connections a la fermeture d'une fenetre
    @Override
    public MessageServiceFactory getServiceFactory() {
        return this.messageSenderServiceFactory;
    }

    private void sendMessageWith(MessageSenderService messageSenderService, String mess) {
        System.out.print("Destination IP address : " + this.ipAddr + "\n");
        System.out.print("Destination port : "+ this.port + "\n");
        System.out.print("Message : "+mess);

        try {
            messageSenderService.sendMessageOn(mess);
            System.out.println(String.format(NOTIFICATION_FORMAT, this.ipAddr, this.port));
        }

        catch (Exception exception) {
            System.err.println(ERROR_MESSAGE);
            System.err.println(exception);
        }
    }

    public void endConnexion() throws IOException {
        TCPMessageSenderService sender =
                (TCPMessageSenderService) this.messageSenderServiceFactory.onTCP();
        sender.endConnection();
    }
    public void freeConnexion() throws IOException {
        TCPMessageSenderService sender =
                (TCPMessageSenderService) this.messageSenderServiceFactory.onTCP();
        sender.freeConnection();
    }
}
