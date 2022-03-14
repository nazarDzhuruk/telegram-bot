package com.foxminded.telebot.keyboard;

import com.foxminded.telebot.service.GenreService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardAction implements KeyboardService {
    private final GenreService genreService;

    public KeyboardAction(GenreService genreService) {
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
