package com.adribast.clavarnak;

import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;
import com.adribast.clavarnak.sender_receiver.MasterListener;
import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;
import com.adribast.clavarnak.ui.SendUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.SwingConstants.CENTER;

public class Window extends JFrame implements ActionListener {

    //this is the list which contains all connected users
    private UsersManager UM = new UsersManager() ;


    private JPanel menu = new JPanel();
    private JPanel usersListPanel = new JPanel();


    public Window (String name, int width, int height) throws VoidStringException, AliasAlreadyExistsException, IOException {

        //Socket qui va ecouter sur le port 1620 si des gens veulent discuter avec nous
        MasterListener master= new MasterListener();
        master.launchListeningThread();

        //definition des layouts pour chaque conteneur
        menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));

        this.addMenuButton("Clavarder");
        this.addMenuButton("Changer pseudo");
        this.addMenuButton("Chatter avec Bast");

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

        //Ajout de la Box "menu" au content pane de la fenetre
        this.setContentPane(menu) ;
        //Et enfin, la rendre visible


        this.setVisible(true);

    }

    //addButton without dimensions
    public void addMenuButton(String title){

        Button ourButton = new Button(title) ;
        ourButton.setAlignmentX(CENTER_ALIGNMENT);

        //espace les cases de 8px
        menu.add(ourButton) ;
        menu.add(Box.createVerticalStrut(10)); //espace les cases de 8px

        ourButton.addActionListener(this);
    }

    //this button is used to contains an user
    public void addUserButton(String title) {
        Button ourButton = new ButtonUser(title) ;
        ourButton.setMaximumSize(ourButton.getMinimumSize());

        usersListPanel.add(ourButton) ;
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

            case "Chatter avec Bast":
                this.addMenuButton("Bast");
                this.setVisible(true);
                break;

            default:
                try {
                    SendUI sendInvitation = new SendUI("127.0.0.1",1620);
                    sendInvitation.onTCP("1025 1026");
                    sendInvitation.freeConnexion();
                    ChatWindow theWindow = new ChatWindow(source.toString(), 400, 500,
                            1025,1026,"127.0.0.1");
                    ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void buildUsersList () {
        JLabel usersListLabel = new JLabel("Choix du destinataire");

        usersListPanel.setLayout(new GridLayout(8,1));


        Font police = new Font("Arial", Font.BOLD, 14);
        usersListLabel.setFont(police);
        usersListLabel.setForeground(Color.white);
        usersListLabel.setHorizontalAlignment(CENTER);

        usersListPanel.add(usersListLabel);
        usersListPanel.setBackground(Color.DARK_GRAY);
        usersListPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE,200));


        ArrayList<User> listOfUsers = UM.getAllUsers() ;

        for (User user : listOfUsers) {
            this.addUserButton(user.toString());
        }

        this.setContentPane(usersListPanel) ;
        this.setVisible(true);

    }

    public void modifAlias () throws Exception {

    ModifAliasWindow aliasWindow = new ModifAliasWindow(UM) ;

    }

}
