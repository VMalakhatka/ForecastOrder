package org.example.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.amqp.AmqpException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {NotEnoughDataException.class})
    protected ResponseEntity<Object> handleNotEnoughDataException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage()+"Data failure may be caused by a lack of communication with the server - check", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {AmqpException.class})
    protected ResponseEntity<Object> handleAmqpException(RuntimeException ex,WebRequest request){
        return handleExceptionInternal(ex,ex.getMessage()+" /n --  Error related to data exchange between microservices --- ", new HttpHeaders(),HttpStatus.REQUEST_TIMEOUT,request);
    }


    @ExceptionHandler(value = {RabbitNotAnswerException.class})
    protected ResponseEntity<Object> handleRabbitNotAnswerException(Exception ex,WebRequest request){
        return handleExceptionInternal(ex,ex.getMessage()+" /n --  Error related to data exchange between microservices --- ", new HttpHeaders(),HttpStatus.REQUEST_TIMEOUT,request);
    }

//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        if (ex instanceof MethodArgumentNotValidException) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Non-valid arguments are received: " + ex.getMessage());
//        }
//        return super.handleExceptionInternal(ex, body, headers, status, request);
//    }


//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
//    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
//        return handleExceptionInternal(ex,ex.getMessage()+" Non-valid arguments are received ",new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
//    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(RuntimeException ex, WebRequest request){
        return handleExceptionInternal(ex,ex.getMessage()+" Non-valid arguments are received ",new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
    }

}
