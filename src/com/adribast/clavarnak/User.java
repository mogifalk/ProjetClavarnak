package com.adribast.clavarnak;

public class User {

    private String alias ;
    private String ip ;


    public User (String alias, String ip) {
        this.ip = ip ;
        this.alias = alias ; /*only UsersManager can call the User constructor
                                 and only AliasEditor can set the alias field*/
    }

    void replaceAlias (String newAlias) {

    this.alias=newAlias;

    }

    public String toString () {
    return alias;
    }

}
