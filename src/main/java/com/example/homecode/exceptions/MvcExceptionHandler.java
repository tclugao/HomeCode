package com.example.homecode.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class MvcExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotAdminException.class)
    @ResponseBody
    public ResponseEntity<String> handleUserNotAdminException(UserNotAdminException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Not Admin");
    }

    @ExceptionHandler(HeaderNoRoleInfoException.class)
    @ResponseBody
    public ResponseEntity<String> handleHeaderNoRoleInfoException(HeaderNoRoleInfoException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Miss Role Info In Header");
    }

    @ExceptionHandler(IncorrectRoleInfoException.class)
    @ResponseBody
    public ResponseEntity<String> handleIncorrectRoleInfoException(IncorrectRoleInfoException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Role Info In Header");
    }
}
