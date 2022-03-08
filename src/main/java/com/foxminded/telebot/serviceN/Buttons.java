package com.foxminded.telebot.serviceN;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface Buttons {
    SendMessage sendGenres(String chatId);
    List<List<InlineKeyboardButton>> genresInButtons();
}
