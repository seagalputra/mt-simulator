package com.infosys.mtsimulator.api.exception;

public class FailedToSaveException extends RuntimeException {
    public FailedToSaveException(String message) {
        super(message);
    }
}
