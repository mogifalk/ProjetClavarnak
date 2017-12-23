package com.adribast.clavarnak;

import com.adribast.clavarnak.sender_receiver.TCPMessageSenderService;
import com.adribast.clavarnak.ui.CommunicationUI;
import com.adribast.clavarnak.ui.ReceiveUI;
import com.adribast.clavarnak.ui.SendUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static com.adribast.clavarnak.Main.logger;

public class ChatWindow extends JFrame implements ActionListener {

    //the conversation that are displayed
    private JPanel writingContainerRight = new JPanel();
    private JPanel writingContainerLeft = new JPanel();
    private boolean test = true;

    private static int xlocation = 700;
    private static int ylocation = 150;

    private JTextArea jtf = new JTextArea("Enter your message");
    static private Date date = new Date();

    private ArrayList<JLabel> conversation = new ArrayList<>();

    private int listenPort;
    private int sendPort;

    private ReceiveUI receiveUI;
    private CommunicationUI sendUI ;

    public FileHandler fh ;

    private Button send = new Button("Send");

    private String name;

    private int width = 400;
    private int height = 550;

    public ChatWindow(String name, int listenPort, int sendPort, String ipDest) throws IOException {


        this.writingContainerLeft.setLayout(new BoxLayout(writingContainerLeft, BoxLayout.PAGE_AXIS));
        this.writingContainerRight.setLayout(new BoxLayout(writingContainerRight, BoxLayout.PAGE_AXIS));

        this.name = name;
        this.listenPort=listenPort;
        this.sendPort = sendPort;

        //On essaye d'avoir un port different en liste a chaque fois
        this.sendUI = new SendUI(ipDest,sendPort);
        this.receiveUI = new ReceiveUI(this.listenPort,this, (SendUI) this.sendUI);

        //if the file exists it appends logs at the end, else it creates a file named with an alias
        this.fh = new FileHandler("logs/"+name+".txt",true);

        logger.addHandler(this.fh);

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
                ,this.name,fh);

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
            }

            else {
                decomposeString(text, conv, nbCharLigne);
            }
        }
    }

    private void decomposeString(String text,ArrayList<JLabel> conv, int nbCharLigne) {
        JLabel label1 = new JLabel(text.substring(0, nbCharLigne));
        conv.add(label1);

        int fin = text.length() / nbCharLigne;
        int i;
        for (i = 1; i <= fin - 1; i++) {

            JLabel label = new JLabel(text.substring(i * nbCharLigne, (i + 1) * nbCharLigne));
            conv.add(label);
        }
        JLabel label2 = new JLabel(text.substring(i * nbCharLigne));
        conv.add(label2);
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

        DateFormat longDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);
        this.conversation.add(new JLabel(longDateFormat.format(date)+"  "));

        addWithReturn(mess,this.conversation);

        //we put an empty line between this message and the next
        this.conversation.add(new JLabel("\n"));

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


        int max =28;
        if (conv.size()>max){
            for (int i=0; i<conv.size()-max;i++){
                conv.remove(conv.get(0));
            }
        }


        if (position == BorderLayout.WEST ){

            addAndRemoveWC(conv,position,this.writingContainerLeft);

        }

        else{

            addAndRemoveWC(conv,position,this.writingContainerRight);

        }


    }
    public String getName(){return this.name;}

    private void addAndRemoveWC(ArrayList<JLabel> conv, String position, JPanel pan){

        this.getContentPane().remove(pan);

        pan.removeAll();

        for (JLabel object: conv) {
            addToContainer(pan,object);
        }


        this.getContentPane().add(pan,position);

        this.test=false;

        this.setVisible(true);

    }

}