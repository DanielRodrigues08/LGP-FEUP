package com.lifecycle.backend.exception;

public class ProcessAlreadyAssignedException extends Exception {
    public ProcessAlreadyAssignedException(String message) {
        super(message);
    }
}