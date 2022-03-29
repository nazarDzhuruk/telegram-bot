package com.foxminded.telebot.handler.callback.implementation;

import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.callback.CallbackHandler;
import com.foxminded.telebot.handler.callback.implementation.helper.Browser;
import com.foxminded.telebot.keyboard.KeyboardFactory;
import com.foxminded.telebot.model.Genre;
import com.foxminded.telebot.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Slf4j
@Component
public class HandleGenreCallback implements CallbackHandler {

    private final GenreService genreService;
    private final KeyboardFactory keyboardFactory;
    private static final String SHOW = "show:";
    private static final String COLON = ":";
    private static final String LOG = "Callback handle: Genre call – ";

    public HandleGenreCallback(GenreService genreService, KeyboardFactory keyboardFactory) {
        this.genreService = genreService;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public List<SendMessage> getDescription(CallbackQuery callbackQuery) {
        log.trace(LOG + "accessed");
        String genre = callbackQuery.getData().split(COLON)[1];

        String chatId = callbackQuery.getMessage().getChatId().toString();
        Genre genreWithLink = genreService.getAll().stream().filter(g -> g.getGenreName().equals(genre))
                .findAny().orElseThrow();

        return Browser.browsePage(chatId, genreWithLink.getLink(), keyboardFactory);
    }

    @Override
    public Callback getCorrectCallBack() {
        return Callback.GENRE;
    }
}
