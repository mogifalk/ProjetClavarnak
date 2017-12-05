package com.adribast.clavarnak;

public class User {

    private String alias ;
    private String firstName ;
    private String lastName ;


    public User (String firstName, String lastName, String alias) {
        this.firstName = firstName ;
        this.lastName = lastName ;
        this.alias = alias ; /*only UsersManager can call the User constructor
                                 and only AliasEditor can set the alias field*/
    }

         private void editAlias (String newPseudo) {

        //vérifier si l'alias existe

    }

    public String getAlias() {
        return alias;
    }

    public String toString () {
    return (alias + " (" + firstName + " " + lastName +")") ;
    }

}
