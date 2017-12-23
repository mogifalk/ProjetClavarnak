package tests;


import com.adribast.clavarnak.UsersManager;
import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersManagerTest {

    @Test(expected = VoidStringException.class)
    public void shouldReturnTrueIfAliasIsEmpty() throws Exception {
        String emptyAlias = "";
        UsersManager users = new UsersManager() ;
        users.addUser(emptyAlias,"1.2.3.4");
    }


    @Test(expected = AliasAlreadyExistsException.class)
    public void shouldReturnTrueIfAliasAlreadyExists() throws Exception {
        UsersManager um = new UsersManager() ;
        um.addUser("sameAlias","1.2.3.4");
        um.addUser("sameAlias","2.3.4.5");
    }

    @Test
    public void shouldReplace() throws Exception {
        UsersManager um = new UsersManager() ;
        um.addUser("firstAlias","1.2.3.4");
        um.addUser("secondAlias","1.2.3.4");
        System.out.println("First alias exists :" + um.aliasExists("firstAlias"));
        System.out.println("Second alias exists :" + um.aliasExists("secondAlias"));
    }
}