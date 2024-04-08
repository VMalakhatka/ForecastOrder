package org.example.exception;

public class NotFindByIDException extends Exception{
    public NotFindByIDException(String message) {
        super(message);
    }
}
