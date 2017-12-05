package com.adribast.clavarnak;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Window extends JFrame implements ActionListener {


    private Pannel pan = new Pannel();
    private Button button1 = new Button("Clavarder") ;
    private Button button2 = new Button("Changer de pseudo") ;
    private Button button3 = new Button("Chatter avec Bast") ;
    private JLabel label = new JLabel() ;

    //Declaration du layout associé au menu
    private Box menu = Box.createVerticalBox();

      //Compteur de clics

    private int compteur = 0;

    public Window (String name, int width, int height) {

        //Définit un titre pour notre fenêtre

        this.setTitle(name);

        //Définit sa taille : 400 pixels de large et 100 pixels de haut

        this.setSize(width, height);

        //Nous demandons maintenant à notre objet de se positionner au centre

        this.setLocationRelativeTo(null);

        //Termine le processus lorsqu'on clique sur la croix rouge

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        //Création d'un bouton dans le content pane
        this.menu.add(button1) ;
        this.menu.add(Box.createVerticalStrut(10)); //espace les cases de 8px
        this.menu.add(button2) ;
        this.menu.add(Box.createVerticalStrut(10)); //espace les cases de 8px
        this.menu.add(button3) ;

        this.button1.addActionListener(this);
        this.button2.addActionListener(this);
        this.button3.addActionListener(this);

        this.menu.add(label) ;

        //On prévient notre JFrame que notre JPanel sera son content pane

        this.setContentPane(pan);


        //Ajout de la Box "menu" au content pane de la fenetre
        this.getContentPane().add(menu) ;
        //Et enfin, la rendre visible

        this.setVisible(true);

    }

    public void addButton(String name, Box b){
        Button ourButton = new Button(name) ;
        b.add(Box.createVerticalStrut(10)); //espace les cases de 8px
        b.add(ourButton) ;
        b.add(Box.createVerticalStrut(10)); //espace les cases de 8px
        ourButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        Object source = evt.getSource();

        if (source == button1) {
            //AFFICHER LA LISTE DES USERS CONNECTES
        } else if (source == button2) {
            //MODIFIER PSEUDO
        }
         else if (source == button3) {
            this.addButton("Bast",this.menu);
            this.setVisible(true);
        }
        else{
            ChatWindow theWindow = new ChatWindow(source.toString(),400,500);
        }
    }
}
