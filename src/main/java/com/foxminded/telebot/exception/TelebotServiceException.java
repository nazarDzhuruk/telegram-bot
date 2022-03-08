package com.foxminded.telebot.exception;

public class TelebotServiceException extends RuntimeException{
    public TelebotServiceException() {
    }

    public TelebotServiceException(String message) {
        super(message);
    }

    public TelebotServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelebotServiceException(Throwable cause) {
        super(cause);
    }

    public TelebotServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
