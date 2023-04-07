package com.security.test.exception;

import org.springframework.http.HttpStatus;




public class NoSuchElementException extends RuntimeException {
    public NoSuchElementException(HttpStatus badRequest, String message) {
        super(message);
    }

  

}
