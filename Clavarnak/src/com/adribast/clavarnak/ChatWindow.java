package com.adribast.clavarnak;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow extends JFrame implements ActionListener {

    private Pannel pan = new Pannel();

    //Une box qui va nous permettre de ranger les boutons
    private Box menu = Box.createVerticalBox();

    public ChatWindow(String name, int width, int height )  {

        this.setTitle(name);

        //Définit sa taille : 400 pixels de large et 100 pixels de haut

        this.setSize(width, height);

        //Nous demandons maintenant à notre objet de se positionner au centre

        this.setLocationRelativeTo(null);

        //Termine le processus lorsqu'on clique sur la croix rouge

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //Declaration du layout associé au menu
        Box menu = Box.createVerticalBox();

        //On prévient notre JFrame que notre JPanel sera son content pane

        this.setContentPane(pan);


        //Ajout de la Box "menu" au content pane de la fenetre
        this.getContentPane().add(menu) ;
        //Et enfin, la rendre visible

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
