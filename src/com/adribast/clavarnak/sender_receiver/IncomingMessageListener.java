package com.adribast.clavarnak.sender_receiver;


import java.io.IOException;

public interface IncomingMessageListener {
    void onNewIncomingMessage(String message) throws IOException, InterruptedException;
}
