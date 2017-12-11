package com.adribast.clavarnak.sender_receiver.factory;

public interface MessageServiceFactory<T> {

    T onTCP();

    T onUDP();
}
