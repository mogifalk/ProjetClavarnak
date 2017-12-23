package com.adribast.clavarnak;

import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;
import com.adribast.clavarnak.sender_receiver.MasterListener;
import com.adribast.clavarnak.sender_receiver.TCPMessageSenderService;
import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;
import com.adribast.clavarnak.ui.SendUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static com.adribast.clavarnak.Main.myAlias;
import static javax.swing.SwingConstants.CENTER;

public class Window extends JFrame implements ActionListener {

    //this is the list which contains all connected users
    private UsersManager UM ;


    private JPanel menu = new JPanel();



    public Window (UsersManager UM) throws VoidStringException, AliasAlreadyExistsException, IOException {

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

        this.setSize(400, 500);

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
    public void addMenuButton(String title){

        Button ourButton = new Button(title) ;
        ourButton.setAlignmentX(CENTER_ALIGNMENT);

        //8px between each button
        menu.add(ourButton) ;
        menu.add(Box.createVerticalStrut(10)); //espace les cases de 8px

        ourButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        Object source = evt.getSource();

        switch (source.toString()) {
            case "Clavarder":
                buildUsersList();
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
    public void buildUsersList () {
        UsersWindow usersWindow = new UsersWindow(this.UM);
    }


    public void modifAlias () throws Exception {

        ModifAliasWindow aliasWindow = new ModifAliasWindow(this.UM) ;

    }

}
