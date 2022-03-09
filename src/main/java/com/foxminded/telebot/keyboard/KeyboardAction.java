package com.foxminded.telebot.keyboard;

import com.foxminded.telebot.model.Genre;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardAction implements KeyboardService {

    @Override
    public List<List<InlineKeyboardButton>> genreButtons(List<Genre> genre) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        genre.forEach(g -> buttons.add(List.of(InlineKeyboardButton.builder()
                .text(g.getGenreName()).callbackData("GENRE" + g.getGenreName()).build())));

        return buttons;
    }
}
