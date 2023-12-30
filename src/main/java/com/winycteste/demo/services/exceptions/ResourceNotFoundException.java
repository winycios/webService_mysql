package com.winycteste.demo.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Object obj) {
        super("Resource not found. ID: " + obj);
    }

}
