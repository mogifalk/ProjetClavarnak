package com.adribast.clavarnak;

import java.awt.*;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;



public class Pannel extends JPanel {

    public void paintComponent(Graphics g){

        try {

            Image img = ImageIO.read(new File("img/lamasticot.jpg"));

            g.setColor(Color.white);

            //On le dessine de sorte qu'il occupe toute la surface

            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            //g.drawImage(img, 0, 0, this);

            //Pour une image de fond

            //g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

            //this.setBackground(Color.ORANGE);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
