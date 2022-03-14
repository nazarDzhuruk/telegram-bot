package com.foxminded.telebot.keyboard;

import com.foxminded.telebot.keyboard.service.KeyboardService;
import com.foxminded.telebot.keyboard.service.KeyboardType;

public interface KeyboardFactory {
    KeyboardService getKeyboard(KeyboardType type);
}
