package com.adribast.clavarnak.sender_receiver;

import java.io.IOException;

public interface MessageReceiverService extends Runnable {

    /**
     * Listen to incoming messages on given port, and notifies the listener of new messages
     *
     * @param port                    The port to listen on
     * @param incomingMessageListener The listener to notify on new incoming messages
     */
    void listen() throws Exception;
    void endConnection() throws IOException;
}
