package com.adribast.clavarnak.sender_receiver.factory;

import java.io.IOException;

public interface MessageServiceFactory<T> {

    T onTCP() throws IOException;

    T onUDP();
}
