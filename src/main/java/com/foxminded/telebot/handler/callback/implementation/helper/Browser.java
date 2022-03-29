package com.foxminded.telebot.handler.callback.implementation.helper;

import com.foxminded.telebot.keyboard.KeyboardFactory;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import com.foxminded.telebot.service.HtmlDataParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Browser {
    private static final String SHOW = "show:";

    private Browser() {
    }

    public static List<SendMessage> browsePage(String chatId, String linkData, KeyboardFactory keyboardFactory) {

        List<String> titles = HtmlDataParser.getTitles(linkData);
        List<String> links = HtmlDataParser.getFilmsLink(linkData);
        List<String> rating = HtmlDataParser.getFilmRating(linkData);

        List<SendMessage> films = IntStream.range(0, titles.size())
                .mapToObj(i -> SendMessage.builder().chatId(chatId).text(titles.get(i) + "\n" + rating.get(i))
                        .replyMarkup(keyboardFactory.getKeyboard(KeyboardType.SHOW).setKeyboard(SHOW + links.get(i)))
                        .build())
                .collect(Collectors.toList());

        films.add(SendMessage.builder().chatId(chatId).text("Navigate")
                .replyMarkup(keyboardFactory.getKeyboard(KeyboardType.PAGE_CONTROL).setKeyboard(linkData)).build());
        return films;
    }
}
