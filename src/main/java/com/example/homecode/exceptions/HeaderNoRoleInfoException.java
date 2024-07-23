package com.example.homecode.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class HeaderNoRoleInfoException extends ResponseStatusException {

    public HeaderNoRoleInfoException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
