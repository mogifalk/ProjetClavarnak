package com.adribast.clavarnak;

import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;

import java.util.ArrayList;
import java.util.List;

public class UsersManager {

    //usersLists contains only the connected users
    private ArrayList<User> usersList;


    public UsersManager() {
        this.usersList = new ArrayList<>();
    }

    //

    public void addUser(User user) throws VoidStringException, AliasAlreadyExistsException {
        this.usersList.add(user);
    }


    public boolean aliasExists(String aliasTest) {
        for (User user : usersList) {
            if (aliasTest.compareTo(user.toString()) == 0) {
                return true;
            }
        }
        return false;
    }


    public User searchUserByAlias(String alias) {

        for (User user : this.usersList)

        {
            if (user.toString().toLowerCase().contains(alias.toLowerCase())) {
                return (user);
            }
        }
        return (null);
    }
        public void printAllUsers (String alias){
            for (User user : usersList) {
                if (user.toString().toLowerCase().contains(alias.toLowerCase())) {
                    System.out.println(user.toString()); //
                    // ############# toString a définir pour User ################
                }
            }
        }

        public ArrayList<User> getAllUsers() {
            return this.usersList;
        }
    }