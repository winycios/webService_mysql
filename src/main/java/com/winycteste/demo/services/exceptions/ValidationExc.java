package com.winycteste.demo.services.exceptions;


public class ValidationExc extends RuntimeException {


    public ValidationExc(String errorMessages) {
            super(errorMessages + " is blank");
    }
}
