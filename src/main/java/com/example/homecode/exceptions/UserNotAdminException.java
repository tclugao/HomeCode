package com.example.homecode.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class UserNotAdminException extends ResponseStatusException {

    public UserNotAdminException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
