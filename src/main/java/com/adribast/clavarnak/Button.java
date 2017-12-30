package com.adribast.clavarnak;

import java.awt.*;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JButton;



public class Button extends JButton implements MouseListener{

    private String name;

    private Image img;

    public Button(String str){

        super(str);

        this.name = str;

        Dimension sizeButton = new Dimension(120, 30) ;

        this.setMaximumSize(sizeButton);

        try {

            img = ImageIO.read(new File("img/fondBouton.png"));

        } catch (IOException e) {

            System.out.println("Erreur à l'ouverture de l'image");
            e.printStackTrace();

        }

        this.addMouseListener(this);

    }


    public void paintComponent(Graphics g){

        Graphics2D g2d = (Graphics2D)g;

        GradientPaint gp = new GradientPaint(0, 0, Color.blue, 0, 20, Color.cyan, true);

        g2d.setPaint(gp);

        g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

        g2d.setColor(Color.black);

        g2d.drawString(this.name, this.getWidth() / 2 - (this.name.length()*8 / 2), (this.getHeight() / 2) + 5);

    }


    public void mouseClicked(MouseEvent event) {

        //Inutile d'utiliser cette méthode ici

    }


    public void mouseEntered(MouseEvent event) {

        //Nous changeons le fond de notre image pour le jaune lors du survol, avec le fichier fondBoutonHover.png

        try {

            img = ImageIO.read(new File("img/fondBoutonHover.png"));

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    public void mouseExited(MouseEvent event) {

        //Nous changeons le fond de notre image pour le vert lorsque nous quittons le bouton, avec le fichier fondBouton.png

        try {

            img = ImageIO.read(new File("img/fondBouton.png"));

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    public void mousePressed(MouseEvent event) {

        //Nous changeons le fond de notre image pour le jaune lors du clic gauche, avec le fichier fondBoutonClic.png

        try {

            img = ImageIO.read(new File("img/fondBoutonClic.png"));

        } catch (IOException e) {

            e.printStackTrace();

        }

    }



    public void mouseReleased(MouseEvent event) {

        //Nous changeons le fond de notre image pour le orange lorsque nous relâchons le clic avec le fichier fondBoutonHover.png si la souris est toujours sur le bouton

        if((event.getY() > 0 && event.getY() < this.getHeight()) && (event.getX() > 0 && event.getX() < this.getWidth())){

            try {

                img = ImageIO.read(new File("img/fondBoutonHover.png"));

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        //Si on se trouve à l'extérieur, on dessine le fond par défaut

        else{

            try {

                img = ImageIO.read(new File("img/fondBouton.png"));

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

    public String toString(){
        return this.name;
    }

}