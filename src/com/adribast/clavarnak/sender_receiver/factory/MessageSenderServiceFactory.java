package com.adribast.clavarnak.sender_receiver.factory;

import com.adribast.clavarnak.sender_receiver.MessageSenderService;
import com.adribast.clavarnak.sender_receiver.TCPMessageSenderService;
import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;

public class MessageSenderServiceFactory implements MessageServiceFactory<MessageSenderService> {

    private int port;
    private String ipAddress;
    private TCPMessageSenderService sender;

    public MessageSenderServiceFactory(int ourPort, String ip) {
        this.port=ourPort;
        this.ipAddress=ip;
        this.sender = new TCPMessageSenderService(this.port,this.ipAddress);
    }

    @Override
    public MessageSenderService onTCP(){
        return this.sender;
    }

    @Override
    public MessageSenderService onUDP() {
        return new UDPMessageSenderService(this.port,this.ipAddress);
    }
}
