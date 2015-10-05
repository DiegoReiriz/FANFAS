package com.diegorc;

/**
 * Created by Diego Reiriz Cores on 2/10/15.
 */
public class Simbolo {

    //string that represents a symbol of the language
    private String cadena;

    //gets the string that represents the symbol
    public String get(){
        return cadena;
    }

    //each symbol needs a string that represents it
    public Simbolo(String cadena){

        this.cadena=cadena;

    }
}
