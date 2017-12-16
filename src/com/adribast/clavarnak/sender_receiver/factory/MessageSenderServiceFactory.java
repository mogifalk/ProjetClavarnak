package com.adribast.clavarnak.sender_receiver.factory;

import com.adribast.clavarnak.sender_receiver.MessageSenderService;
import com.adribast.clavarnak.sender_receiver.TCPMessageSenderService;
import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;

import java.io.IOException;

public class MessageSenderServiceFactory implements MessageServiceFactory<MessageSenderService> {

    private int port;
    private String ipAddress;
    private TCPMessageSenderService sender;

    public MessageSenderServiceFactory(int ourPort, String ip) throws IOException {
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
