package com.adribast.clavarnak.com.exceptions;

public class AliasAlreadyExistsException extends Exception {
    public AliasAlreadyExistsException () {
        super() ;
    }

    public AliasAlreadyExistsException (String msg) {
        super(msg) ;
    }
}
