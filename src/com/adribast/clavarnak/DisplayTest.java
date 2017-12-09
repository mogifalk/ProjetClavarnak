package com.adribast.clavarnak;


import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;

import java.awt.*;

public class DisplayTest {

    public static void main(String[] args) throws VoidStringException, AliasAlreadyExistsException {

        new Window("menu", 400, 500);

    }
}