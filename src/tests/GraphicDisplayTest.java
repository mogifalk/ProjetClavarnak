package tests;

import com.adribast.clavarnak.GraphicDisplay;
import com.adribast.clavarnak.User;
import com.adribast.clavarnak.UsersManager;
import com.adribast.clavarnak.Window;
import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;

import static org.junit.Assert.*;

public class GraphicDisplayTest {

    @Test
    public void displayUsersList() throws Exception {
        Window window = new Window("Clavarnak",400,600) ;
        GraphicDisplay graphicDisplay = new GraphicDisplay() ;

        User user1 = new User("Adri","Gonza","bite") ;
        User user2 = new User("Joseph","le noir","LeNoir") ;
        User user3 = new User("Alban","Le Carbonnier de la Morsangliere","Prov0ck") ;

        UsersManager users = new UsersManager() ;
        users.addUser(user1);
        users.addUser(user2);
        users.addUser(user3);



    }
}