package com.adribast.clavarnak;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class GraphicDisplay {

    public GraphicDisplay () {

    }

    //
    public void usersList (Window window, Box box, UsersManager usersMan) {

        //declaration of a new pane which will come on foreground
        Pannel userChoicePane = new Pannel() ;

        userChoicePane.add(box) ;

        ArrayList<User> listOfUsers = usersMan.getAllUsers() ;

        for (User user : listOfUsers) {
            window.addUserButton(user.toString(),box);
        }

        window.setContentPane(userChoicePane) ;
        window.setVisible(true);
        
    }
}
