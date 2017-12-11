package com.adribast.clavarnak.sender_receiver.factory;

import com.adribast.clavarnak.sender_receiver.IncomingMessageListener;
import com.adribast.clavarnak.sender_receiver.MessageReceiverService;
import com.adribast.clavarnak.sender_receiver.TCPMessageReceiverService;
import com.adribast.clavarnak.sender_receiver.UDPMessageReceiverService;

public class MessageReceiverServiceFactory implements MessageServiceFactory<MessageReceiverService> {

    int port;
    public IncomingMessageListener incomingMessageListener;

    public MessageReceiverServiceFactory(IncomingMessageListener ourIncomingMessageListener,int ourPort){
        this.port=ourPort;
        this.incomingMessageListener=ourIncomingMessageListener;
    }

    @Override
    public MessageReceiverService onTCP() {
        return new TCPMessageReceiverService(this.incomingMessageListener,this.port);
    }

    @Override
    public MessageReceiverService onUDP() {
        return new UDPMessageReceiverService(this.incomingMessageListener,this.port);
    }
}
