package com.adribast.clavarnak;



import com.adribast.clavarnak.sender_receiver.UDPMessageReceiverService;
import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;

import java.net.InetAddress;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class Main {

    public static Logger logger = Logger.getLogger(Main.class.getName());
    public static final int configPort = 4242;
    public static final String broadcastIP = "192.168.1.255";
    public static String myAlias = "noPseudo";
    private static UsersManager UM = new UsersManager();
    public static ArrayList<String> conversationActive = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        logger.setLevel(Level.FINE);

        UM.addUser("Adri", "127.0.0.1");
        UM.addUser("Bast", "2.2.3.4");
        UM.addUser("Joseph", "3.2.3.4");
        UM.addUser("Banban", "4.2.3.4");
        UM.addUser("Laurent", "5.2.3.4");
        UM.addUser("Mathieu", "6.2.3.4");
        UM.addUser("LeNoir", "3.2.3.4");

        UDPMessageReceiverService aliasListener = new UDPMessageReceiverService(configPort, UM);
        Thread aliasListenThread = new Thread(aliasListener);
        aliasListenThread.start();

        UDPMessageSenderService aliasRequester = new UDPMessageSenderService(configPort, broadcastIP);
        aliasRequester.sendMessageOn("PLEASE SEND YOUR ALIAS");

        Thread.sleep(2000); //

        ModifAliasWindow aliasWindow = new ModifAliasWindow(UM);


        while (myAlias.compareTo("noPseudo") == 0) {Thread.sleep(500);}


        Window test = new Window("menu", 400, 500, UM);

        aliasWindow.toFront();

    }

}