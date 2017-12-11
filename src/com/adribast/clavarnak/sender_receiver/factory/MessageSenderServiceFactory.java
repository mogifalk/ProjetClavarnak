package com.adribast.clavarnak.sender_receiver.factory;

import com.adribast.clavarnak.sender_receiver.MessageSenderService;
import com.adribast.clavarnak.sender_receiver.TCPMessageSenderService;
import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;

public class MessageSenderServiceFactory implements MessageServiceFactory<MessageSenderService> {

    @Override
    public MessageSenderService onTCP() {
        return new TCPMessageSenderService();
    }

    @Override
    public MessageSenderService onUDP() {
        return new UDPMessageSenderService();
    }
}
