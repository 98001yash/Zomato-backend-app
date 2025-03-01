package com.company.project.Zomato.ZomatoApp.exceptions;

public class RuntimeConflictException extends RuntimeException{

    public RuntimeConflictException(){

    }

    public RuntimeConflictException(String message){
        super(message);
    }
}
