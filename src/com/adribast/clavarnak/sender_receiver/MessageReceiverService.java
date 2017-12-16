package com.adribast.clavarnak.sender_receiver;

import java.io.IOException;

public interface MessageReceiverService extends Runnable {


    void listen() throws Exception;
    void endConnection() throws IOException;

}
