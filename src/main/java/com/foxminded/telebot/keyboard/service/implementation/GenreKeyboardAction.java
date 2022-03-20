package com.foxminded.telebot.keyboard.service.implementation;

import com.foxminded.telebot.keyboard.service.KeyboardService;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import com.foxminded.telebot.service.GenreService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GenreKeyboardAction implements KeyboardService {
    private static final String LOG = "Keyboard service: Genre – ";
    private final GenreService genreService;

    public GenreKeyboardAction(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public List<List<InlineKeyboardButton>> getButtons(String setCallBack) {
        log.trace(LOG + "accessed");
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        genreService.getAll().forEach(g -> buttons.add(List.of(InlineKeyboardButton.builder()
                .text(g.getGenreName()).callbackData(setCallBack + g.getGenreName()).build())));

        log.info(LOG + "buttons has been generated");
        return buttons;
    }

    @Override
    public KeyboardType getType() {
        return KeyboardType.GENRES;
    }
}
