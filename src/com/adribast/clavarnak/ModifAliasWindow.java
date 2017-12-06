package com.adribast.clavarnak;

import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.CENTER;

public class ModifAliasWindow extends JFrame implements ActionListener {

    /*##### A INITIALISER LORS DE LA PREMIERE INSTALLATION #####*/
    private User currentUser = new User("Bast","Gonza","mogifalk") ;
    /*#####################################################################################*/

    private JPanel container = new JPanel();

    public JTextField writingField = new JTextField();

    private JLabel label = new JLabel("Quel pseudo voulez-vous ?\n");

    UsersManager UM;

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

        writingContainer.add(label);
        writingContainer.add(writingField);

        container.add(writingContainer, BorderLayout.CENTER);

        this.setContentPane(container);

        this.setVisible(true);

        Button buttonCheck = new Button("Verifier") ;
        buttonCheck.setAlignmentX(CENTER_ALIGNMENT);

        container.add(buttonCheck) ;
        buttonCheck.addActionListener(this);

        if (UM.aliasExists(writingField.getText())) {
            Graphics graphics = writingContainer.getGraphics() ;
            writingContainer.paintComponents(graphics);
        }

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        switch (source.toString()) {
            case "Verifier":
                String alias = writingField.getText();

                if (UM.aliasExists(alias)) {
                    JLabel label = new JLabel("Ce pseudo est déjà pris");
                    label.setHorizontalAlignment(CENTER);

                    container.add(label);
                    this.setContentPane(container);
                }

                else {
                    //currentUser.replaceAlias(alias);
                }
                break;
        }
    }
}

