package com.adribast.clavarnak.sender_receiver;

public interface MessageSenderService {

    void sendMessageOn(String ipAddress, int port, String message) throws Exception;
}
