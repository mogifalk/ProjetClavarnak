package com.adribast.clavarnak.sender_receiver;

import com.adribast.clavarnak.ChatWindow;
import com.adribast.clavarnak.UsersManager;
import com.adribast.clavarnak.sender_receiver.factory.MessageReceiverServiceFactory;

import java.io.IOException;
import java.util.Scanner;

import static com.adribast.clavarnak.Main.conversationActive;

public class MasterListener implements IncomingMessageListener {

    private final MessageReceiverServiceFactory messageReceiverServiceFactory;
    private final int port;
    private UsersManager UM;

    public MasterListener(UsersManager UM) throws IOException {
        this.port = 1620;
        this.messageReceiverServiceFactory = new MessageReceiverServiceFactory(this,this.port);
        this.UM = UM;
    }

    @Override
    public void onNewIncomingMessage(String message) throws IOException{


        Scanner s = new Scanner(message);
        s.useDelimiter(" ");
        String remoteUserName =s.next();
        int port1=Integer.parseInt(s.next());
        int port2=Integer.parseInt(s.next());

        System.out.println("New message invitation from : " + remoteUserName+
                "he wants to talk you on port : " + port2 + "you can talk to " +
                        "him on port :"+ port1 + "\n");

        TCPMessageReceiverService receiver = (TCPMessageReceiverService) this.messageReceiverServiceFactory.onTCP();
        receiver.setConnectionEnded(true);

        ChatWindow chat = new ChatWindow(remoteUserName,port2,port1,UM.getIpOf(remoteUserName));
        conversationActive.add(remoteUserName);
        System.out.println("add conv : " + remoteUserName);
        
    }

    public void launchListeningThread() {
        System.out.print("Listening on port: " + this.port + "\n");
        try {
             Thread listenThread = new Thread(this.messageReceiverServiceFactory.onTCP());
            TCPMessageReceiverService receiver =
                    (TCPMessageReceiverService) this.messageReceiverServiceFactory.onTCP();
            receiver.setConnectionEnded(false);
            receiver.setMultipleListen();

            listenThread.start();
        } catch (Exception e) {
            System.out.println("[ERROR] An error occured while trying to listen on port");
            e.printStackTrace();
        }
    }


}
