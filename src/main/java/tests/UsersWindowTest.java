package tests;

import com.adribast.clavarnak.UsersManager;
import com.adribast.clavarnak.UsersWindow;
import com.adribast.clavarnak.Window;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class UsersWindowTest {

    @Test
    public void generatePortsTest() throws Exception{
        int p1=35;
        int p2 =37;

        UsersManager UM = new UsersManager();
        Window menu = new Window(UM);
        UsersWindow UW = new UsersWindow(UM,menu);
        for (int i =0;i <10;i++){
            int [] result = UW.generatePorts();

            if (result[1]>65535||result[1]<1024){
                System.out.println("Mauvais port !!" + result[1]);
            }
            if (result[0]>65535||result[0]<1024){
                System.out.println("Mauvais port !!" + result[0]);
            }
            System.out.println(result[0] + " " + result[1]);
        }
    }
}