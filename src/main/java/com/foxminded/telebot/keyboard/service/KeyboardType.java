package com.foxminded.telebot.keyboard.service;

public enum KeyboardType {
    GENRES("genres"),
    SHOW("show"),
    PAGE_CONTROL("page");

    private final String name;

    KeyboardType(String name) {
        this.name = name;
    }
}
