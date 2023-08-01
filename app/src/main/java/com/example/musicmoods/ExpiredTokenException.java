package com.example.musicmoods;

public class ExpiredTokenException extends Exception {
    public ExpiredTokenException(String str){
        super(str);
    }
}
