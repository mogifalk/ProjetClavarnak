package com.adribast.clavarnak.sender_receiver.factory;

import com.adribast.clavarnak.sender_receiver.*;

public class MessageSenderServiceFactory implements MessageServiceFactory<MessageSenderService> {

    @Override
    public MessageSenderService onTCP() { return new TCPMessageSenderService();
    }

    @Override
    public MessageSenderService onUDP() {
        return new UDPMessageSenderService();
    }

    @Override
    public MessageSenderService onBroadcast() { return new BroadcastSenderService(); }

}
