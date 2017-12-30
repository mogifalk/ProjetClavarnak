package com.adribast.clavarnak;

import com.adribast.clavarnak.sender_receiver.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
