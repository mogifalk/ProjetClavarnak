package com.adribast.clavarnak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class ChatWindow extends JFrame implements ActionListener {



    private static int xlocation = 700;
    private static int ylocation = 150;


    private JTextArea jtf = new JTextArea("Enter your message");
    static private Date date = new Date();

    public ArrayList<JLabel> conversation = new ArrayList<JLabel>();



    ChatWindow(String name, int width, int height)  {



        xlocation = xlocation +20;
        ylocation = ylocation + 20;

        this.conversation.add(new JLabel(date.toString()));

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

    //a continuer pour ajouter un truc pour pas couper les mots
    public void addWithReturn(String text){

        int nbCharLigne = 27;

        int fin = text.length()/nbCharLigne;
        System.out.println(fin);

        if (text.length()<nbCharLigne){
            JLabel label = new JLabel(text);
            this.conversation.add(label);
        }

        else{

            JLabel label1 = new JLabel(text.substring(0,nbCharLigne));
            this.conversation.add(label1);
            int i;
            for (i=1 ; i<=fin-1;i++){

                JLabel label = new JLabel(text.substring(i*nbCharLigne,(i+1)*nbCharLigne));
                this.conversation.add(label);
        }
            JLabel label2 = new JLabel(text.substring(i*nbCharLigne));
            this.conversation.add(label2);

        }
    }

    public void addToContainer (JPanel container, JLabel lab){
        container.add(lab);
        //container.add(Box.createVerticalStrut(10)); //espace les cases de 8px
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        String mess = this.jtf.getText();

        JPanel writingContainer = new JPanel();

        writingContainer.setLayout(new BoxLayout(writingContainer, BoxLayout.PAGE_AXIS));

        addWithReturn(mess);

        for (JLabel object: conversation) {
            addToContainer(writingContainer,object);
        }

        //writingContainer.add(label);

        //writingContainer.add(Box.createVerticalStrut(10)); //espace les cases de 8px


        this.getContentPane().add(writingContainer,BorderLayout.EAST);

        this.jtf.setText("");
        System.out.println(mess);

        this.setVisible(true);
    }
}