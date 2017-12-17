package com.adribast.clavarnak;

import com.adribast.clavarnak.sender_receiver.*;
import com.adribast.clavarnak.ui.SendUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import static com.adribast.clavarnak.Main.*;

public class MainWindowListener extends WindowAdapter {

    public void windowClosing(WindowEvent e){
        try {
            UDPMessageSenderService closingNotifSender = new UDPMessageSenderService(configPort,broadcastIP);
            closingNotifSender.sendMessageOn("DISCONNECTED USER :" + myAlias + ":");
        }

        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
