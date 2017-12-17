package com.adribast.clavarnak.sender_receiver.factory;

import com.adribast.clavarnak.UsersManager;
import com.adribast.clavarnak.sender_receiver.IncomingMessageListener;
import com.adribast.clavarnak.sender_receiver.MessageReceiverService;
import com.adribast.clavarnak.sender_receiver.TCPMessageReceiverService;
import com.adribast.clavarnak.sender_receiver.UDPMessageReceiverService;
import com.adribast.clavarnak.ui.SendUI;

import java.io.IOException;

public class MessageReceiverServiceFactory implements MessageServiceFactory<MessageReceiverService> {

    private int port;

    private IncomingMessageListener incomingMessageListener;
    private TCPMessageReceiverService receiver;

    public MessageReceiverServiceFactory(IncomingMessageListener ourIncomingMessageListener,int ourPort,SendUI send) throws IOException {
        this.port=ourPort;
        this.incomingMessageListener=ourIncomingMessageListener;
        this.receiver = new TCPMessageReceiverService(this.incomingMessageListener,this.port,send);
    }

    public MessageReceiverServiceFactory(IncomingMessageListener ourIncomingMessageListener,int ourPort) throws IOException {
        this.port=ourPort;
        this.incomingMessageListener=ourIncomingMessageListener;
        this.receiver = new TCPMessageReceiverService(this.incomingMessageListener,this.port);
    }

    @Override
    public MessageReceiverService onTCP(){
        return this.receiver;
    }

    @Override
    public MessageReceiverService onUDP() { return this.receiver; }
}
