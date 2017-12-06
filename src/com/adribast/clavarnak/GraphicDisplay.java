package com.adribast.clavarnak;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class GraphicDisplay {

    public GraphicDisplay () {

    }

    //
    public void displayUsersList (Window window, UsersManager usersMan) {
        Box listUsersBox = Box.createVerticalBox() ;

        ArrayList<User> listOfUsers = usersMan.getAllUsers() ;

        for (User user : listOfUsers) {
            window.addButton(user.toString(),listUsersBox);
        }
        
    }
}
