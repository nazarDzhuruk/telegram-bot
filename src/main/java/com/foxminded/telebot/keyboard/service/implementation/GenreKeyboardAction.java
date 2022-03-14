package com.foxminded.telebot.keyboard.service.implementation;

import com.foxminded.telebot.keyboard.service.KeyboardService;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import com.foxminded.telebot.service.GenreService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreKeyboardAction implements KeyboardService {
    private final GenreService genreService;

    public GenreKeyboardAction(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public List<List<InlineKeyboardButton>> getButtons(String setCallBack) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        genreService.getAll().forEach(g -> buttons.add(List.of(InlineKeyboardButton.builder()
                .text(g.getGenreName()).callbackData(setCallBack + g.getGenreName()).build())));

        return buttons;
    }

    @Override
    public KeyboardType getType() {
        return KeyboardType.GENRES;
    }
}
