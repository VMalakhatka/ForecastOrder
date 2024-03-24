package org.example.exeption;

public class NotFindByID extends Exception{
    public NotFindByID(String message) {
        super(message);
    }
}
