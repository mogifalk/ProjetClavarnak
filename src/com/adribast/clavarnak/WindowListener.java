package com.adribast.clavarnak;

import com.adribast.clavarnak.sender_receiver.*;
import com.adribast.clavarnak.ui.SendUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import static com.adribast.clavarnak.Main.*;

public class WindowListener extends WindowAdapter {

    private TCPMessageSenderService sendService;
    private String convName;

    public WindowListener(TCPMessageSenderService sServ, String convName){

        this.sendService = sServ;
        this.convName = convName;
    }


    public void windowClosing(WindowEvent e){
        try {
            System.out.println("ENDING CONNECTION\n");
            conversationActive.remove(this.convName);
            sendService.endConnection();




        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
