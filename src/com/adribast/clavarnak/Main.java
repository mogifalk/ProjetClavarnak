package com.adribast.clavarnak;


import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;
import com.adribast.clavarnak.sender_receiver.BroadcastListener;
import com.adribast.clavarnak.ui.ReceiveUI;

public class Main {

    public static int broadcastPort = 1620 ;
    public static String myAlias = "mogifalk"; /////////////////////////////////////////
    public static UsersManager UM = new UsersManager() ;

    public static void main(String[] args) throws Exception {
        User user1 = new User("Adri","") ;
        User user2 = new User("JosephLeNoir","") ;
        User user3 = new User("Alban","") ;

        BroadcastListener broadcastListener = new BroadcastListener();
        Window test = new Window("menu", 400, 500);

    }

}