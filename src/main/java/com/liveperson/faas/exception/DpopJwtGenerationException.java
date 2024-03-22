package com.liveperson.faas.exception;

public class DpopJwtGenerationException extends Exception {

    public DpopJwtGenerationException(String message) {
        super(message);
    }

    public DpopJwtGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
