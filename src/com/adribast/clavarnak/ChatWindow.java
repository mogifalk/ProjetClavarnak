package com.adribast.clavarnak;


import com.adribast.clavarnak.sender_receiver.TCPMessageSenderService;
import com.adribast.clavarnak.ui.CommunicationUI;
import com.adribast.clavarnak.ui.ReceiveUI;
import com.adribast.clavarnak.ui.SendUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static com.adribast.clavarnak.Main.logger;

public class ChatWindow extends JFrame implements ActionListener {



    private static int xlocation = 700;
    private static int ylocation = 150;


    private JTextArea jtf = new JTextArea("Enter your message");
    static private Date date = new Date();

    private ArrayList<JLabel> conversation = new ArrayList<>();

    //port d'écoute
    private int listenPort;
    //port d'envoie
    private int sendPort;


    private ReceiveUI receiveUI;
    private CommunicationUI sendUI ;

    public FileHandler fh ;

    private Button send = new Button("Send");

    private String name;

    private int width = 400;
    private int height = 500;




    public ChatWindow(String name, int listenPort, int sendPort, String ipDest) throws IOException {

        this.name = name;
        this.listenPort=listenPort;
        this.sendPort = sendPort;


        //On essaye d'avoir un port different en liste a chaque fois
        this.sendUI = new SendUI(ipDest,sendPort);
        this.receiveUI = new ReceiveUI(listenPort,this, (SendUI) this.sendUI);

        //if the file exists it appends logs at the end, else it creates a file named with an alias
        this.fh = new FileHandler(name+".txt",true);

        logger.addHandler(this.fh);

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

        this.receiveUI.onTCP("");

        //Nous permet d'effectuer des actions a la fermeture de la fenetre
        WindowListener wListener;
        wListener = new WindowListener((TCPMessageSenderService) this.sendUI.getServiceFactory().onTCP()
                ,this.name);

        this.addWindowListener(wListener);

    }


    //permet de decouper les message en lignes
    public void addWithReturn(String text,ArrayList<JLabel> conv){

        if (text!=null) {

            int nbCharLigne = 23;

            int fin = text.length() / nbCharLigne;

            if (text.length() < nbCharLigne) {
                JLabel label = new JLabel(text);
                conv.add(label);
            } else {

                JLabel label1 = new JLabel(text.substring(0, nbCharLigne));
                conv.add(label1);
                int i;
                for (i = 1; i <= fin - 1; i++) {

                    JLabel label = new JLabel(text.substring(i * nbCharLigne, (i + 1) * nbCharLigne));
                    conv.add(label);
                }
                JLabel label2 = new JLabel(text.substring(i * nbCharLigne));
                conv.add(label2);

            }
        }
    }

    private void addToContainer (JPanel container, JLabel lab){
        container.add(lab);
        //container.add(Box.createVerticalStrut(10)); //espace les cases de 8px
    }


    @Override
    public void actionPerformed(ActionEvent evt) {

        String mess = this.jtf.getText();
        LogRecord logMess = new LogRecord(Level.FINE,"SENT : \n" +"\n"+mess);
        this.fh.publish(logMess);

        addWithReturn(mess,this.conversation);

        printConversation(this.conversation,BorderLayout.EAST);
        //writingContainer.add(label);

        //writingContainer.add(Box.createVerticalStrut(10)); //espace les cases de 8px

        this.jtf.setText("");
        System.out.println(mess);

        try {
            this.sendUI.onTCP(mess);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setVisible(true);
    }

    public void printConversation(ArrayList<JLabel> conv,String position ){

        JPanel writingContainer = new JPanel();

        writingContainer.setLayout(new BoxLayout(writingContainer, BoxLayout.PAGE_AXIS));


        for (JLabel object: conv) {
            addToContainer(writingContainer,object);
        }

        this.getContentPane().add(writingContainer,position);

        this.setVisible(true);

    }
    public String getName(){return this.name;}

}