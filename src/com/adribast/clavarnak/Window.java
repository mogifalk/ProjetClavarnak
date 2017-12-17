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
import java.util.Set;

import static com.adribast.clavarnak.Main.myAlias;
import static javax.swing.SwingConstants.CENTER;

public class Window extends JFrame implements ActionListener {

    //this is the list which contains all connected users
    private UsersManager UM ;


    private JPanel menu = new JPanel();



    public Window (String name, int width, int height, UsersManager UM) throws VoidStringException, AliasAlreadyExistsException, IOException {

        this.UM = UM ;

        //Socket qui va ecouter sur le port 1620 si des gens veulent discuter avec nous
        MasterListener master= new MasterListener(this.UM);
        master.launchListeningThread();

        //definition des layouts pour chaque conteneur
        menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));

        this.addMenuButton("Clavarder");
        this.addMenuButton("Changer pseudo");
        this.addMenuButton("Chatter avec Bast");

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
                    String ip = UM.getIpOf(source.toString());

                    SendUI sendInvitation = new SendUI(ip,1620);

                    sendInvitation.onTCP(myAlias+" 1030 1031");
                    //on libere la socket pour la reutiliser si besoin
                    sendInvitation.freeConnexion();

                    ChatWindow theWindow = new ChatWindow(source.toString(), 400, 500,
                            1030,1031,ip);
                    ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void buildUsersList () {
        UsersWindow usersWindow = new UsersWindow(this.UM);
    }


    public void modifAlias () throws Exception {

        ModifAliasWindow aliasWindow = new ModifAliasWindow(this.UM) ;

    }

}
