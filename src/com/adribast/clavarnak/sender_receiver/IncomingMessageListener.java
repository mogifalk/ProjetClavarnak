package com.adribast.clavarnak.sender_receiver;

import javax.swing.*;
import java.util.ArrayList;

public interface IncomingMessageListener {
    void onNewIncomingMessage(String message);
}
