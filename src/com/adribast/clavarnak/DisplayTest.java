package com.adribast.clavarnak;


import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;

import java.awt.*;
import java.io.IOException;

public class DisplayTest {

    public static void main(String[] args) throws VoidStringException, AliasAlreadyExistsException, IOException {

        new Window("menu", 400, 500);

    }
}