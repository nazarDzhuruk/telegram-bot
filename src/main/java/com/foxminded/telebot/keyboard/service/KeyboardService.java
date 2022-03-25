package com.foxminded.telebot.keyboard.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface KeyboardService {
    ReplyKeyboard setKeyboard(String setCallBack);
    KeyboardType getType();
}
