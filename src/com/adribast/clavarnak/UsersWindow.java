package com.adribast.clavarnak;

import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;
import com.adribast.clavarnak.ui.SendUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import static com.adribast.clavarnak.Main.broadcastIP;
import static com.adribast.clavarnak.Main.configPort;
import static com.adribast.clavarnak.Main.myAlias;
import static javax.swing.SwingConstants.CENTER;

public class UsersWindow extends JFrame implements ActionListener {

    private JPanel usersListPanel = new JPanel();
    JLabel usersListLabel = new JLabel("Choix du destinataire");

    private UsersManager UM;

    public UsersWindow(UsersManager UM) {

        //Définit un titre pour notre fenêtre
        this.setTitle("Choix du destinataire");
        //Définit sa taille : 400 pixels de large et 100 pixels de haut
        this.setSize(300,600 );
        //Nous demandons maintenant à notre objet de se positionner au centre
        this.setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //Ajout de la Box "usersList" au content pane de la fenetre
        this.setContentPane(usersListPanel);
        //Et enfin, la rendre visible
        this.setVisible(true);
        this.UM=UM;

        usersListPanel.setLayout(new GridLayout(8, 1));

        Font police = new Font("Arial", Font.BOLD, 14);
        usersListLabel.setFont(police);
        usersListLabel.setForeground(Color.white);
        usersListLabel.setHorizontalAlignment(CENTER);

        usersListPanel.add(usersListLabel);
        usersListPanel.setBackground(Color.DARK_GRAY);
        usersListPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 200));

        for (String currentAlias : this.UM.getAliases()) {
            this.addUserButton(currentAlias);
        }

        this.setVisible(true);

    }

    public void addUserButton(String title) {
        Button ourButton = new ButtonUser(title) ;
        ourButton.setMaximumSize(ourButton.getMinimumSize());

        usersListPanel.add(ourButton) ;
        ourButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        Object source = evt.getSource();
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
    }
}