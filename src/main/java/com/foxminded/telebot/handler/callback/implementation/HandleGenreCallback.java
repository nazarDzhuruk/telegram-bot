package com.foxminded.telebot.handler.callback.implementation;

import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.callback.CallbackHandler;
import com.foxminded.telebot.keyboard.KeyboardFactory;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import com.foxminded.telebot.model.Genre;
import com.foxminded.telebot.service.GenreService;
import com.foxminded.telebot.service.HtmlDataParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        Genre genreWithLink = genreService.getAll().stream().filter(g -> g.getGenreName().equals(genre))
                .findAny().orElseThrow();

        List<String> links = HtmlDataParser.getFilmsLink(genreWithLink.getLink(), "");
        List<String> titles = HtmlDataParser.getTitles(genreWithLink.getLink(), "");
        List<String> rating = HtmlDataParser.getFilmRating(genreWithLink.getLink());

        log.info(LOG + genre + " about generated");

        return IntStream.range(0, links.size()).mapToObj(i -> SendMessage.builder()
                .chatId(callbackQuery.getMessage().getChatId().toString())
                .text(titles.get(i) + "\n" + rating.get(i))
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(keyboardFactory.getKeyboard(KeyboardType.SHOW)
                                .getButtons(SHOW + links.get(i))).build())
                .build()).collect(Collectors.toList());
    }

    @Override
    public Callback getCorrectCallBack() {
        return Callback.GENRE;
    }
}
