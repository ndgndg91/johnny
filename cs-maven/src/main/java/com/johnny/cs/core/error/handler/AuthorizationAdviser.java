package com.johnny.cs.core.error.handler;

import com.johnny.cs.core.error.exception.ForbiddenException;
import com.johnny.cs.core.error.exception.UnAuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorizationAdviser {

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<String> unAuthorization(){
        return ResponseEntity.status(401).body("API_KEY 누락!");
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> forbidden(){
        return ResponseEntity.status(403).body("API_KEY 틀림!");
    }
}
