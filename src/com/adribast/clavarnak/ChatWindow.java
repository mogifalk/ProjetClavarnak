package com.adribast.clavarnak;

import com.adribast.clavarnak.sender_receiver.factory.MessageReceiverServiceFactory;
import com.adribast.clavarnak.sender_receiver.factory.MessageSenderServiceFactory;
import com.adribast.clavarnak.ui.CommunicationUI;
import com.adribast.clavarnak.ui.ReceiveUI;
import com.adribast.clavarnak.ui.SendUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

public class ChatWindow extends JFrame implements ActionListener {



    private static int xlocation = 700;
    private static int ylocation = 150;


    private JTextArea jtf = new JTextArea("Enter your message");
    static private Date date = new Date();

    private ArrayList<JLabel> conversation = new ArrayList<>();
    private ArrayList<JLabel> hisconversation = new ArrayList<>();

    //port d'écoute
    private static int listenPort = 1102;
    //port d'envoie
    private static int sendPort = 1095;


    private ReceiveUI receiveUI;
    private CommunicationUI sendUI ;


    private Button send = new Button("Send");



    ChatWindow(String name, int width, int height)  {
        //On essaye d'avoir un port different en liste a chaque fois
        this.receiveUI = new ReceiveUI(listenPort);
        this.sendUI = new SendUI(new MessageSenderServiceFactory(),"127.0.0.1",sendPort);

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
        this.jtf.getScrollableTracksViewportWidth();
        this.jtf.setLineWrap(true);
        KeyListener action = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    send.doClick(0);
                }
            }
        };


        writingZone.add(this.jtf,BorderLayout.WEST);
        writingZone.add(send,BorderLayout.EAST);



        this.getContentPane().add(writingZone,BorderLayout.SOUTH);
        this.getContentPane().setForeground(Color.DARK_GRAY);

        //container.add(writingZone, BorderLayout.SOUTH);


        send.addActionListener(this);

        jtf.addKeyListener(action);

        //Et enfin, la rendre visible

        this.setVisible(true);
        System.out.println("test");

        this.receiveUI.onTCP("");


        String receivedMess = this.receiveUI.getConversation();
        addWithReturn(receivedMess, hisconversation);

        printConversation(hisconversation, BorderLayout.WEST);
    }

    //permet de decouper les message en lignes
    private void addWithReturn(String text,ArrayList<JLabel> conv){

        int nbCharLigne = 23;

        int fin = text.length()/nbCharLigne;

        if (text.length()<nbCharLigne){
            JLabel label = new JLabel(text);
            conv.add(label);
        }

        else{

            JLabel label1 = new JLabel(text.substring(0,nbCharLigne));
            conv.add(label1);
            int i;
            for (i=1 ; i<=fin-1;i++){

                JLabel label = new JLabel(text.substring(i*nbCharLigne,(i+1)*nbCharLigne));
                conv.add(label);
        }
            JLabel label2 = new JLabel(text.substring(i*nbCharLigne));
            conv.add(label2);

        }
    }

    private void addToContainer (JPanel container, JLabel lab){
        container.add(lab);
        //container.add(Box.createVerticalStrut(10)); //espace les cases de 8px
    }


    @Override
    public void actionPerformed(ActionEvent evt) {

        String mess = this.jtf.getText();


        addWithReturn(mess,this.conversation);

        printConversation(this.conversation,BorderLayout.EAST);
        //writingContainer.add(label);

        //writingContainer.add(Box.createVerticalStrut(10)); //espace les cases de 8px

        this.jtf.setText("");
        System.out.println(mess);

        this.sendUI.onTCP(mess);

        this.setVisible(true);
    }

    public void printConversation(ArrayList<JLabel> conv,String position ){

        JPanel writingContainer = new JPanel();

        writingContainer.setLayout(new BoxLayout(writingContainer, BoxLayout.PAGE_AXIS));


        for (JLabel object: conv) {
            addToContainer(writingContainer,object);
        }

        this.getContentPane().add(writingContainer,position);

    }
}