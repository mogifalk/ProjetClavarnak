package com.adribast.clavarnak;



import com.adribast.clavarnak.sender_receiver.UDPMessageReceiverService;
import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class Main {

    //this object allows us to save the conversations
    static Logger logger = Logger.getLogger(Main.class.getName());

    //listening port for the broadcast to get connected people
    static final int configPort = 4242;

    static final String broadcastIP = "255.255.255.255";

    //Initialising our pseudo
    public static String myAlias = "noPseudo";

    private static UsersManager UM = new UsersManager();

    //list of active conversations so we can't open the conversation window
    //more than one time
    public static ArrayList<String> conversationActive = new ArrayList<>();



    public static void main(String[] args) throws Exception {

        logger.setLevel(Level.FINE);

        UM.addUser("Adri", "127.0.0.1");

        //Listening socket for receiving the pseudo and ip of
        // new users connecting
        UDPMessageReceiverService aliasListener = new UDPMessageReceiverService(configPort, UM);
        Thread aliasListenThread = new Thread(aliasListener);
        aliasListenThread.start();

        UDPMessageSenderService aliasRequester = new UDPMessageSenderService(configPort, broadcastIP);
        aliasRequester.sendMessageOn("PLEASE SEND YOUR ALIAS");

        //So we are sure the response is received before we do more
        Thread.sleep(1000);

        ModifAliasWindow aliasWindow = new ModifAliasWindow(UM);


        while (myAlias.compareTo("noPseudo") == 0) {Thread.sleep(500);}


        Window menu = new Window(UM);

        aliasWindow.toFront();

    }

}