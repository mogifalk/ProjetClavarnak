package com.adribast.clavarnak;

import com.adribast.clavarnak.sender_receiver.MessageReceiverService;
import com.adribast.clavarnak.sender_receiver.MessageSenderService;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class WindowListener extends WindowAdapter {

    private MessageReceiverService receiveService;
    private MessageSenderService sendService;

    public WindowListener(MessageReceiverService rServ,MessageSenderService sServ){
        this.receiveService = rServ;
        this.sendService = sServ;
    }


    public void windowClosing(WindowEvent e){
        try {
            sendService.sendMessageOn("close connection");
            receiveService.endConnection();
            System.out.println("\nListen connection ended\n");
            sendService.endConnection();
            System.out.println("\nsend connection ended\n");

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
