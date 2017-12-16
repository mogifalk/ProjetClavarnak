package com.adribast.clavarnak.ui;

import com.adribast.clavarnak.sender_receiver.factory.MessageServiceFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public interface CommunicationUI {
    void onUDP(String mess);

    void onTCP(String mess) throws IOException;

    MessageServiceFactory getServiceFactory();
}
