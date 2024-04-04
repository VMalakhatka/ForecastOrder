package org.example.exception;

import org.springframework.amqp.AmqpException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {NotEnoughDataException.class})
    protected ResponseEntity<Object> handleNotEnoughData(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage()+"Data failure may be caused by a lack of communication with the server - check", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {AmqpException.class})
    protected ResponseEntity<Object> handleAmqpException(RuntimeException ex,WebRequest request){
        return handleExceptionInternal(ex,ex.getMessage()+" /n --  Error related to data exchange between microservices --- ", new HttpHeaders(),HttpStatus.REQUEST_TIMEOUT,request);
    }

    @ExceptionHandler(value = {RabbitNotAnswerException.class})
    protected ResponseEntity<Object> handleRabbitNotAnswerException(RuntimeException ex,WebRequest request){
        return handleExceptionInternal(ex,ex.getMessage()+" /n --  Error related to data exchange between microservices --- ", new HttpHeaders(),HttpStatus.REQUEST_TIMEOUT,request);
    }
}
