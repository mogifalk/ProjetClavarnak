package com.adribast.clavarnak;

import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;

import java.util.ArrayList;

public class UsersManager {

        //usersLists contains only the connected users
        private ArrayList<User> usersList ;


        public UsersManager () {
            this.usersList = new ArrayList<>() ;
        }

        //

        public void addUser(String firstName, String lastName, String alias) throws VoidStringException, AliasAlreadyExistsException {

            if (this.estNul(firstName)) {
                throw new VoidStringException("Exception : void pseudo") ;
            }

            if (this.estNul(lastName)){
                throw new VoidStringException("Exception : void first name") ;
            }

            else if (this.estNul(alias)) {
                throw new VoidStringException("Exception : void last name") ;
            }

            if (this.aliasExists(alias)) {
                throw new AliasAlreadyExistsException("Ce pseudo est déjà utilisé");
            }

            User newOne = new User(firstName,lastName,alias) ;
            this.usersList.add(newOne);
        }

        public void printAllConnectedUsers() {
            System.out.println(this.usersList.toString());
        }

        public void searchUserByAlias(String alias) {

            for (User user : usersList) {
                if (user.getAlias().toLowerCase().contains(alias.toLowerCase())) {
                    System.out.println(user.toString()); //
                    // ############# toString a définir pour User ################
                }
            }
        }

        public boolean aliasExists (String aliasTest) {
            for (User user : usersList) {
                if (aliasTest.compareTo(user.getAlias()) == 0) {
                   return true ;
                }
            }
            return false ;
        }

        private boolean estNul (String var) {
        return (var == null || var=="") ;
    }


}
