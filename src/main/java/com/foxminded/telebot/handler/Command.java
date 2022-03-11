package com.foxminded.telebot.handler;

public enum Command {
    WELCOME("hi", "hello"),
    ABOUT("about", "about"),
    START("start", "Start Bot"),
    STOP("stop", "Stop messaging"),
    LIKE("like", "Like post"),
    GENRES("genres", "Show genres");

    private final String name;
    private final String description;

    Command(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
