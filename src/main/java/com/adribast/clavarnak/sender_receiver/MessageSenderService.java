package com.adribast.clavarnak.sender_receiver;

import java.io.IOException;

public interface MessageSenderService {

    void sendMessageOn(String message) throws Exception;
    void endConnection() throws IOException;
}
