package com.adribast.clavarnak;


import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersManagerTest {

    @Test(expected = AliasAlreadyExistsException.class)
    public void shouldReturnTrueIfAliasAlreadyExists() throws Exception {
        String alias1 = "toto" ;
        UsersManager users = new UsersManager() ;
        users.addUser("Jean","Ferrat", alias1);

        users.addUser("Michel","Sardou",alias1);
    }

    @Test
    public void shouldPrintAListOfUsers() throws Exception {
        UsersManager users = new UsersManager() ;
        users.addUser("Jean","Ferrat", "t0t0");
        users.addUser("Michel","Sardou","t1t1");
        users.addUser("Daniel","Balavoine","t3t3");

        users.printAllConnectedUsers();
    }

}