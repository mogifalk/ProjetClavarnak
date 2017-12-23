package com.adribast.clavarnak;

import com.adribast.clavarnak.com.exceptions.AliasAlreadyExistsException;
import com.adribast.clavarnak.com.exceptions.VoidStringException;
import java.io.IOException;
import java.util.*;

public class UsersManager {

    //usersLists contains only the connected users
    private Hashtable<String, String> usersTable;


    public UsersManager() {

        this.usersTable = new Hashtable<>();
    }

    //

    public void addUser(Token alias, String ip) throws VoidStringException, AliasAlreadyExistsException {
        if ((alias != null && ip != null) || (alias.compareTo("")!=0 && ip.compareTo("")!=0)) {
            //si le pseudo à ajouter existe déjà -> levée d'exception
            if (aliasExists(alias)) {
                throw new AliasAlreadyExistsException("Tentative de remplacement d'un pseudo existant");
            }

            //si l'@ip à ajouter existe déjà -> modification du pseudo associé
            else if (ipExists(ip)) {
                removeAliasWithThisIP(ip) ;
                usersTable.put(alias,ip);
            }

            //sinon on ajoute simplement l'entrée
            else {
                this.usersTable.put(alias, ip);
            }
        }

        else {
            throw new VoidStringException();
        }
    }

    private void removeAliasWithThisIP(String ip) throws VoidStringException {
        String aliasWithSameIP=null;

        //recherche du pseudo associé à l'ip en double
        for(Map.Entry entry: usersTable.entrySet()){
            if(ip.equals(entry.getValue())){
                 aliasWithSameIP = entry.getKey().toString();
                break; //breaking because its one to one map
            }
        }

        if (aliasWithSameIP!=null) {
            this.usersTable.remove(aliasWithSameIP, ip);
        }

        else {throw new VoidStringException();}

    }

    public void delUser(String alias) {
        this.usersTable.remove(alias);
    }

    public boolean aliasExists(String aliasTest) {
        return this.usersTable.containsKey(aliasTest);
    }

    public boolean ipExists(String ip) {
        return this.usersTable.containsValue(ip);
    }

    public String getIpOf (String alias) {
        return this.usersTable.get(alias);
    }

    public Set<String> getAliases () {
        return this.usersTable.keySet();
    }
}