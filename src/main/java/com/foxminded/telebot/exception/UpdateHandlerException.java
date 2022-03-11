package com.foxminded.telebot.exception;

public class UpdateHandlerException extends RuntimeException{
    public UpdateHandlerException() {
    }

    public UpdateHandlerException(String message) {
        super(message);
    }

    public UpdateHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateHandlerException(Throwable cause) {
        super(cause);
    }

    public UpdateHandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
