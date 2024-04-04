package org.example.exception;

public class RabbitNotAnswerException extends Exception{
    public RabbitNotAnswerException(String message) {
        super(message);
    }
}
