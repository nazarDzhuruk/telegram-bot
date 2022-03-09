package com.foxminded.telebot.exception;

public class HtmlDataParserException extends RuntimeException{
    public HtmlDataParserException() {
    }

    public HtmlDataParserException(String message) {
        super(message);
    }

    public HtmlDataParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public HtmlDataParserException(Throwable cause) {
        super(cause);
    }

    public HtmlDataParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
