package com.adribast.clavarnak;



import com.adribast.clavarnak.sender_receiver.UDPMessageReceiverService;
import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;

import java.net.InetAddress;
import java.util.ArrayList;

public class Main {

    public static final int configPort = 4242 ;
    public static final String broadcastIP = "192.168.1.255";
    public static String myAlias = "noPseudo" ;
    private static UsersManager UM = new UsersManager();
    public static ArrayList<String> conversationActive = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        UM.addUser("Adri","127.0.0.1");
        UM.addUser("Bast","2.2.3.4");
        UM.addUser("Joseph","3.2.3.4");
        UM.addUser("Banban","4.2.3.4");
        UM.addUser("Laurent","5.2.3.4");
        UM.addUser("Mathieu","6.2.3.4");
        UM.addUser("LeNoir","3.2.3.4");

        UDPMessageReceiverService aliasListener = new UDPMessageReceiverService(configPort, UM);
        Thread aliasListenThread = new Thread(aliasListener);
        aliasListenThread.start();

        UDPMessageSenderService aliasRequester = new UDPMessageSenderService(configPort,broadcastIP);
        aliasRequester.sendMessageOn("PLEASE SEND YOUR ALIAS");

        Thread.sleep(2000);

        System.out.println(InetAddress.getLocalHost().getHostAddress().toString());
        ModifAliasWindow aliasWindow = new ModifAliasWindow(UM);

        Window test = new Window("menu", 400, 500, UM);

        aliasWindow.toFront();

    }

}