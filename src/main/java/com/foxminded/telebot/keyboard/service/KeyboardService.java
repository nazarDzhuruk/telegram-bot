package com.foxminded.telebot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface KeyboardService {
    List<List<InlineKeyboardButton>> getButtons(String setCallBack);
    KeyboardType getType();
}
