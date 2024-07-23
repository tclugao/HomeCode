package com.example.homecode.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectRoleInfoException extends ResponseStatusException {
    public IncorrectRoleInfoException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
