package com.adribast.clavarnak.ui;

import javax.swing.*;
import java.util.ArrayList;

public interface CommunicationUI {
    void onUDP(String mess);

    void onTCP(String mess);
}
