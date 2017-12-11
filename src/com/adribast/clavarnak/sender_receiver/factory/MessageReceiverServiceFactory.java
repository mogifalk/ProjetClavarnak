package com.adribast.clavarnak.sender_receiver.factory;

import com.adribast.clavarnak.sender_receiver.MessageReceiverService;
import com.adribast.clavarnak.sender_receiver.TCPMessageReceiverService;
import com.adribast.clavarnak.sender_receiver.UDPMessageReceiverService;

public class MessageReceiverServiceFactory implements MessageServiceFactory<MessageReceiverService> {
    @Override
    public MessageReceiverService onTCP() {
        return new TCPMessageReceiverService();
    }

    @Override
    public MessageReceiverService onUDP() {
        return new UDPMessageReceiverService();
    }
}
