package com.foxminded.telebot.handler.message;

public enum Command {
    ABOUT("about", "About bot, author and open source"),
    START("start", "Start Bot, register you as new user"),
    STOP("stop", "Stop messaging, delete you as user"),
    GENRES("genres", "Show genres"),
    HELP("help", "Show commands");

    private final String name;
    private final String description;

    Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }
}
