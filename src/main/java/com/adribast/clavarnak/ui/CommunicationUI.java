package com.adribast.clavarnak.ui;

import com.adribast.clavarnak.sender_receiver.factory.MessageServiceFactory;
import java.io.IOException;

public interface CommunicationUI {
    void onUDP(String mess);

    void onTCP(String mess) throws IOException;

    MessageServiceFactory getServiceFactory();
}
