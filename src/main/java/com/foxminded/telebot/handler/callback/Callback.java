package com.foxminded.telebot.handler.callback;

public enum Callback {
    GENRE("genres"),
    SHOW("show"),
    NEXT("next"),
    PREVIOUS("previous");

    private final String name;

    Callback(String name) {
        this.name = name;
    }
}
