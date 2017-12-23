package com.adribast.clavarnak.ui;

import com.adribast.clavarnak.ChatWindow;
import com.adribast.clavarnak.sender_receiver.IncomingMessageListener;
import com.adribast.clavarnak.sender_receiver.MessageReceiverService;
import com.adribast.clavarnak.sender_receiver.factory.MessageReceiverServiceFactory;
import com.adribast.clavarnak.sender_receiver.factory.MessageServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static com.adribast.clavarnak.Main.conversationActive;
import static com.adribast.clavarnak.Main.logger;

public class ReceiveUI implements CommunicationUI, IncomingMessageListener {

    private static final String ERROR_MESSAGE = "[ERROR] An error occured while trying to listen on port";

    private final MessageReceiverServiceFactory messageReceiverServiceFactory;
    private int port;

    //pour pouvoir ecrire les messages reçus dans la fenetre correspondante
    private ChatWindow chat;

    //cette liste va nous permettre d'afficher les messages reçus
    private ArrayList<JLabel> conversation;
    static private Date date = new Date();

    public ReceiveUI(int ourPort, ChatWindow chat, SendUI sender) throws IOException {
        this.messageReceiverServiceFactory = new MessageReceiverServiceFactory(
                this,ourPort,sender);
        this.port = ourPort;
        this.chat = chat;
        this.conversation = new ArrayList<>();
    }

    @Override
    public void onUDP(String mess) {
        launchListeningThread(messageReceiverServiceFactory.onUDP());
    }

    @Override
    public void onTCP(String mess) throws IOException {
        launchListeningThread(messageReceiverServiceFactory.onTCP());
    }

    //on doit pouvoir le recuperer pour fermer la connection quand la fenetre se ferme
    @Override
    public MessageServiceFactory getServiceFactory() {
        return this.messageReceiverServiceFactory;
    }

    @Override
    public void onNewIncomingMessage(String message) {
        if (message.toUpperCase().compareTo("CLOSE CONNECTION")==0) {
            this.chat.dispose();
            System.out.println("removinf conv : "+this.chat.getName());
            conversationActive.remove(this.chat.getName());
        }
        else {
            System.out.println("NEW MESSAGE : " + message + "\n");

            LogRecord logRcvMess = new LogRecord(Level.FINE,  "RECEIVED : \n" + message);
            chat.fh.publish(logRcvMess);

            this.conversation.add(new JLabel(date.toString()));

            //on transforme le message reçu en plusieurs qui ont pour longueur max une ligne
            this.chat.addWithReturn(message, conversation);

            //on affiche la conversation dans la fenetre du cote gauche
            this.chat.printConversation(conversation, BorderLayout.WEST);
        }
    }

    private void launchListeningThread(MessageReceiverService messageReceiverService) {
        System.out.print("Listenning on port: " + this.port + "\n");
        try {
            Thread listenThread = new Thread(messageReceiverService);
            listenThread.start();
        } catch (Exception exception) {
            System.err.println(ERROR_MESSAGE);
            System.err.println(exception);
        }
    }



}
