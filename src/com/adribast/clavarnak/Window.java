package com.adribast.clavarnak;

import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {

    //this is the list which contains all connected users
    private UsersManager UM = new UsersManager() ;

    private GraphicDisplay graphicDisplay = new GraphicDisplay() ;

    private Box menu = Box.createVerticalBox();
    private Box listUsersBox = Box.createVerticalBox();


    public Window (String name, int width, int height) throws VoidStringException, AliasAlreadyExistsException {

        listUsersBox.setBackground(Color.DARK_GRAY);
        listUsersBox.setPreferredSize(new Dimension(Integer.MAX_VALUE,200));

        //listUsersPannel.setPreferredSize(new Dimension (this.getWidth(),this.getHeight()));

        this.addButton("Clavarder",menu);
        this.addButton("Changer pseudo",menu);
        this.addButton("Chatter avec Bast",menu);

        User user1 = new User("Adri","Gonza","bite") ;
        User user2 = new User("Joseph","le noir","LeNoir") ;
        User user3 = new User("Alban","Le Carbonnier de la Morsangliere","Prov0ck") ;

        UM.addUser(user1);
        UM.addUser(user2);
        UM.addUser(user3);

        //Définit un titre pour notre fenêtre

        this.setTitle(name);

        //Définit sa taille : 400 pixels de large et 100 pixels de haut

        this.setSize(width, height);

        //Nous demandons maintenant à notre objet de se positionner au centre

        this.setLocationRelativeTo(null);

        //Termine le processus lorsqu'on clique sur la croix rouge

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel();
        this.menu.add(label) ;

        //On prévient notre JFrame que notre JPanel sera son content pane

        Pannel pan = new Pannel();
        this.setContentPane(pan);


        //Ajout de la Box "menu" au content pane de la fenetre
        this.getContentPane().add(menu) ;
        //Et enfin, la rendre visible


        this.setVisible(true);

    }

    //addButton without dimensions
    private void addButton(String title, Box b){
        Button ourButton = new Button(title) ;
        ourButton.setAlignmentX(CENTER_ALIGNMENT);    //alignés au centre

        b.add(Box.createVerticalStrut(10)); //espace les cases de 8px
        b.add(ourButton) ;
        b.add(Box.createVerticalStrut(10)); //espace les cases de 8px

        ourButton.addActionListener(this);
    }

    //this button is used to contains an user
    public void addUserButton(String title, Box b) {
        Button ourButton = new ButtonUser(title) ;
        ourButton.setMaximumSize(ourButton.getMinimumSize());

        b.add(ourButton) ;
        ourButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent evt) {

        Object source = evt.getSource();

        switch (source.toString()) {
            case "Clavarder":
                graphicDisplay.usersList(this, listUsersBox, this.UM);
                break;

            case "Changer pseudo":
                //MODIFIER PSEUDO
                break;

            case "Chatter avec Bast":
                this.addButton("Bast", this.menu);
                this.setVisible(true);
                break;

            default:
                ChatWindow theWindow = new ChatWindow(source.toString(), 400, 500);
                break;
        }
    }
}
