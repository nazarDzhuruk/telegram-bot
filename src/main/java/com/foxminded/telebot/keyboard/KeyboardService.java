package com.foxminded.telebot.keyboard;

import com.foxminded.telebot.model.Genre;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface KeyboardService {
    List<List<InlineKeyboardButton>> genreButtons(List<Genre> genre);
}
