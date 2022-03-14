package com.foxminded.telebot.handler.callback;

public enum Callback {
    GENRES("genres"),
    SHOW("show");

    private final String name;

    Callback(String name) {
        this.name = name;
    }
}
