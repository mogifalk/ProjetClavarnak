package com.adribast.clavarnak;


import com.adribast.clavarnak.ui.SendUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static com.adribast.clavarnak.Main.conversationActive;
import static com.adribast.clavarnak.Main.myAlias;
import static javax.swing.SwingConstants.CENTER;

public class UsersWindow extends JFrame implements ActionListener {

    private JPanel usersListPanel = new JPanel();

    private UsersManager UM;

    private Window menu;

    public UsersWindow(UsersManager UM, Window menu) {

        this.menu=menu;
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

        JLabel usersListLabel = new JLabel("Choix du destinataire");
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

        addUserButton("Rafraichir");

    }

    private void addUserButton(String title) {
        Button ourButton = new ButtonUser(title) ;
        ourButton.setMaximumSize(ourButton.getMinimumSize());

        usersListPanel.add(ourButton) ;
        ourButton.addActionListener(this);
    }

    public int[] generatePorts(){
        int port1 =0;
        int port2 =0;
        while(port1==port2){
            port1 = 1024 + (int)(Math.random() * ((65535 - 1024) + 1));
            port2 = 1024 + (int)(Math.random() * ((65535 - 1024) + 1));
        }

        int[] result = {port1,port2};
        return result;

    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        Object source = evt.getSource();

        try {

            //click on the refresh button
            if (source.toString() == "Rafraichir") {
                this.menu.displayUsersList();
                this.dispose();
            } else {
                //if there is no active chat with this user, open a new ChatWindow
                if (!conversationActive.contains(source.toString())) {

                    System.out.println("Nouvelle conversation active avec " + source.toString() + "\n");
                    String ip = UM.getIpOf(source.toString());

                    SendUI sendInvitation = new SendUI(ip, 1620);

                    int[] ports = this.generatePorts();

                    sendInvitation.onTCP(myAlias + " " + ports[0] + " " + ports[1]);

                    //on libere la socket pour la reutiliser si besoin
                    sendInvitation.freeConnection();

                    ChatWindow theWindow = new ChatWindow(source.toString(),
                            ports[0], ports[1], ip);
                    conversationActive.add(source.toString());
                } else {
                    System.out.println("Conversation deja ouverte !");
                }
            }
        }

        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
}