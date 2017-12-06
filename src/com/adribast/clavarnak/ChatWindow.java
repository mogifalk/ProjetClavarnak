package com.adribast.clavarnak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow extends JFrame implements ActionListener {


    private static int xlocation = 700;
    private static int ylocation = 150;


    private JTextArea jtf = new JTextArea("Enter your message");






    ChatWindow(String name, int width, int height)  {


        xlocation = xlocation +20;
        ylocation = ylocation + 20;

        this.setTitle(name);

        //Définit sa taille : 400 pixels de large et 100 pixels de haut

        this.setSize(width, height);

        //Nous demandons maintenant à notre objet de se positionner au centre

        this.setLocation(xlocation,ylocation);

        //Termine le processus lorsqu'on clique sur la croix rouge

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel container = new JPanel();
        container.setBackground(Color.DARK_GRAY);
        container.setMaximumSize(new Dimension(width,height));

        JPanel writingZone = new JPanel();

        writingZone.setBackground(Color.white);
        writingZone.setLayout(new BoxLayout(writingZone,BoxLayout.LINE_AXIS));
        writingZone.setPreferredSize(new Dimension(width, height/7));


        Font police = new Font("Arial", Font.ITALIC, 10);

        //Gestion of the writing zone


        this.jtf.setFont(police);
        this.jtf.setMaximumSize(new Dimension(width*3/4,height/7));
        this.jtf.setForeground(Color.BLACK);
        this.jtf.setLineWrap(true);
        this.jtf.getScrollableTracksViewportWidth();

        writingZone.add(this.jtf,BorderLayout.WEST);
        Button send = new Button("Send");
        writingZone.add(send,BorderLayout.EAST);



        this.getContentPane().add(writingZone,BorderLayout.SOUTH);
        this.getContentPane().setForeground(Color.DARK_GRAY);

        //container.add(writingZone, BorderLayout.SOUTH);


        send.addActionListener(this);


        //Et enfin, la rendre visible

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String mess = this.jtf.getText();
        this.jtf.setText("");
        System.out.println(mess);

    }
}