package com.demo.hms.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
