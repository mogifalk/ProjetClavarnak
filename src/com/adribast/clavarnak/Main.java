package com.adribast.clavarnak;



import com.adribast.clavarnak.sender_receiver.UDPMessageReceiverService;
import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;

import java.net.InetAddress;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class Main {

    //this object allows us to save the conversations
    public static Logger logger = Logger.getLogger(Main.class.getName());

    //listening port for the broadcast to get connected people
    public static final int configPort = 4242;

    public static final String broadcastIP = "255.255.255.255";

    public static String myAlias = "noPseudo";
    private static UsersManager UM = new UsersManager();
    public static ArrayList<String> conversationActive = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        logger.setLevel(Level.FINE);

        UM.addUser("Adri", "127.0.0.1");

        UDPMessageReceiverService aliasListener = new UDPMessageReceiverService(configPort, UM);
        Thread aliasListenThread = new Thread(aliasListener);
        aliasListenThread.start();

        UDPMessageSenderService aliasRequester = new UDPMessageSenderService(configPort, broadcastIP);
        aliasRequester.sendMessageOn("PLEASE SEND YOUR ALIAS");

        Thread.sleep(2000); //

        ModifAliasWindow aliasWindow = new ModifAliasWindow(UM);


        while (myAlias.compareTo("noPseudo") == 0) {Thread.sleep(500);}


        Window test = new Window(UM);

        aliasWindow.toFront();

    }

}