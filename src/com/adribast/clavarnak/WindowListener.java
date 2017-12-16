package com.adribast.clavarnak;

import com.adribast.clavarnak.sender_receiver.MessageReceiverService;
import com.adribast.clavarnak.sender_receiver.MessageSenderService;
import com.adribast.clavarnak.sender_receiver.TCPMessageReceiverService;
import com.adribast.clavarnak.sender_receiver.TCPMessageSenderService;
import com.adribast.clavarnak.ui.SendUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class WindowListener extends WindowAdapter {

    private TCPMessageReceiverService receiveService;
    private TCPMessageSenderService sendService;

    public WindowListener(TCPMessageReceiverService rServ, TCPMessageSenderService sServ){
        this.receiveService = rServ;
        this.sendService = sServ;
    }


    public void windowClosing(WindowEvent e){
        try {

            System.out.println("BEFORE\n");
            sendService.endConnection();

            SendUI send = new SendUI("127.0.0.1",this.receiveService.getPort());
            send.onTCP("close connection");
            System.out.println("AFTER\n");


        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
