package com.infosys.mtsimulator.api.exception;

public class FailedToSendException extends RuntimeException {
    public FailedToSendException(String message) {
        super(message);
    }
}
