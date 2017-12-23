package com.adribast.clavarnak;

import com.adribast.clavarnak.sender_receiver.MasterListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Window extends JFrame implements ActionListener {

    //this is the list which contains all connected users
    private UsersManager UM ;


    private JPanel menu = new JPanel();


    public Window (UsersManager UM) throws IOException {

        this.UM = UM ;

        //This socket will listen on port 1620 to know if some people want to start
        //a conversation
        MasterListener master= new MasterListener(this.UM);
        master.launchListeningThread();

        //definition of layouts for every container
        menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));

        this.addMenuButton("Clavarder");
        this.addMenuButton("Changer pseudo");

        //Title of our window

        this.setTitle("Menu");

        //Size of the window : 400 x 500 pixels

        this.setSize(300, 200);

        //We set the position to the center

        this.setLocationRelativeTo(null);

        //Terminate the process if we click on the red cross

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //We add the menu box to the content pane
        this.setContentPane(menu) ;

        //Finally we set it visible
        this.setVisible(true);

        //allows us to send a message to the other users when we close the window
        //end finish the program
        MainWindowListener wListener;
        wListener = new MainWindowListener();

        this.addWindowListener(wListener);
    }

    //addButton without dimensions
    private void addMenuButton(String title){

        Button ourButton = new Button(title) ;
        ourButton.setAlignmentX(CENTER_ALIGNMENT);

        //8px between each button
        menu.add(ourButton,BorderLayout.CENTER) ;
        menu.add(Box.createVerticalStrut(10)); //espace les cases de 8px

        ourButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        Object source = evt.getSource();

        switch (source.toString()) {
            case "Clavarder":
                displayUsersList();
                break;

            case "Changer pseudo":
                try {
                    modifAlias();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


        }
    }

    //This function create a window with all the users that are connected
    private void displayUsersList () {
        UsersWindow usersWindow = new UsersWindow(this.UM);
    }


    private void modifAlias () {

        ModifAliasWindow aliasWindow = new ModifAliasWindow(this.UM) ;

    }

}
