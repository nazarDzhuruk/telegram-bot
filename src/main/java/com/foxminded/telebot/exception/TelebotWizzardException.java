package com.foxminded.telebot.exception;

public class TelebotWizzardException extends RuntimeException{
    public TelebotWizzardException() {
    }

    public TelebotWizzardException(String message) {
        super(message);
    }

    public TelebotWizzardException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelebotWizzardException(Throwable cause) {
        super(cause);
    }

    public TelebotWizzardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
