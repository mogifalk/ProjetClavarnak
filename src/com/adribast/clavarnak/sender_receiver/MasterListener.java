package com.adribast.clavarnak.sender_receiver;

import com.adribast.clavarnak.ChatWindow;
import com.adribast.clavarnak.UsersManager;
import com.adribast.clavarnak.sender_receiver.factory.MessageReceiverServiceFactory;
import tests.UsersManagerTest;

import java.io.IOException;
import java.util.Scanner;

public class MasterListener implements IncomingMessageListener {

    private static final String ERROR_MESSAGE = "[ERROR] An error occured while trying to listen on port";

    private final MessageReceiverServiceFactory messageReceiverServiceFactory;
    private final int port;
    private UsersManager UM;

    public MasterListener(UsersManager UM) throws IOException {
        this.port = 1621;
        this.messageReceiverServiceFactory = new MessageReceiverServiceFactory(this,this.port);
        this.UM = UM;
    }

    @Override
    public void onNewIncomingMessage(String message) throws IOException, InterruptedException {


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

        ChatWindow chat = new ChatWindow(remoteUserName, 400, 500,
                port2,port1,UM.getIpOf(remoteUserName));


    }

    public void launchListeningThread() {
        System.out.print("Listenning on port: " + this.port + "\n");
        try {
             Thread listenThread = new Thread(this.messageReceiverServiceFactory.onTCP());
            TCPMessageReceiverService receiver =
                    (TCPMessageReceiverService) this.messageReceiverServiceFactory.onTCP();
            receiver.setConnectionEnded(false);
            receiver.setMultipleListen();

            listenThread.start();
        } catch (Exception exception) {
            System.err.println(ERROR_MESSAGE);
            System.err.println(exception);
        }
    }


}
