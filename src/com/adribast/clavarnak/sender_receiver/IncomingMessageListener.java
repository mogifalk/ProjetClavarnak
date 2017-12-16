package com.adribast.clavarnak.sender_receiver;


public interface IncomingMessageListener {
    void onNewIncomingMessage(String message);
}
