package org.example.exeption;

public class RabbitNotAnswer extends Exception{
    public RabbitNotAnswer(String message) {
        super(message);
    }
}
