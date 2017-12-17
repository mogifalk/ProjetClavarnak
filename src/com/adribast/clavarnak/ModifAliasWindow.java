package com.adribast.clavarnak;

import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import static com.adribast.clavarnak.Main.broadcastIP;
import static com.adribast.clavarnak.Main.configPort;
import static com.adribast.clavarnak.Main.myAlias;
import static javax.swing.SwingConstants.CENTER;

public class ModifAliasWindow extends JFrame implements ActionListener {

    private JPanel container = new JPanel();

    private JTextField writingField = new JTextField();

    private UsersManager UM;

    public ModifAliasWindow(UsersManager UM) throws Exception {
        //Définit un titre pour notre fenêtre
        this.setTitle("Changement pseudo");
        //Définit sa taille : 400 pixels de large et 100 pixels de haut
        this.setSize(300,200 );
        //Nous demandons maintenant à notre objet de se positionner au centre
        this.setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //Ajout de la Box "menu" au content pane de la fenetre
        this.setContentPane(container) ;
        //Et enfin, la rendre visible
        this.setVisible(true);
        this.UM=UM;

        container.setBackground(Color.white);

        container.setLayout(new BoxLayout(container,BoxLayout.PAGE_AXIS));

        JPanel writingContainer = new JPanel();

        Font police = new Font("Arial", Font.BOLD, 14);

        writingField.setFont(police);

        writingField.setPreferredSize(new Dimension(150, 40));

        writingField.setForeground(Color.BLUE);

        JLabel label = new JLabel("Quel pseudo voulez-vous ?\n");
        writingContainer.add(label);
        writingContainer.add(writingField);

        container.add(writingContainer, BorderLayout.CENTER);

        this.setContentPane(container);

        this.setVisible(true);

        writingField.addActionListener(this);

        /*Button buttonCheck = new Button("Verifier") ;
        buttonCheck.setAlignmentX(CENTER_ALIGNMENT);

        container.add(buttonCheck) ;
        buttonCheck.addActionListener(this);

        if (UM.aliasExists(writingField.getText())) {
            Graphics graphics = writingContainer.getGraphics() ;
            writingContainer.paintComponents(graphics);
        }*/

    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        try {
            writingField.getText();

            if (UM.aliasExists(writingField.getText())) {
                JLabel label = new JLabel("Ce pseudo est déjà pris");
                label.setHorizontalAlignment(CENTER);

                container.add(label);
                this.setContentPane(container);
            }

            else {
                //si le pseudo est unique on le communique en broadcast
                String newAlias = writingField.getText();
                String myIP = InetAddress.getLocalHost().getHostAddress();

                myAlias = newAlias;
                UM.addUser(myAlias, myIP);
                UDPMessageSenderService aliasSender = new UDPMessageSenderService(configPort, broadcastIP);
                aliasSender.sendMessageOn(myAlias);

                this.dispose();
            }
        }

        catch (Exception e) {
                    e.printStackTrace();

        }
    }
}

