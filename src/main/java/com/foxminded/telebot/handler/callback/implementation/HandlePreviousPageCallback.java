package com.foxminded.telebot.handler.callback.implementation;

import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.callback.CallbackHandler;
import com.foxminded.telebot.keyboard.KeyboardFactory;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import com.foxminded.telebot.service.HtmlDataParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HandlePreviousPageCallback implements CallbackHandler {

    private static final String COLON = ":";
    private static final String PAGE = "page/";
    private final KeyboardFactory keyboardFactory;

    public HandlePreviousPageCallback(KeyboardFactory keyboardFactory) {
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public List<SendMessage> getDescription(CallbackQuery callbackQuery) {
        String linkData;
        int number;

        String link = callbackQuery.getData().split(COLON)[1];
        String[] page = link.split(PAGE);

        try {
            number = Integer.parseInt(page[1]) - 1;
            linkData = page[0] + PAGE + number;
        } catch (IndexOutOfBoundsException r) {
            linkData = link;
        }

        String chatId = callbackQuery.getMessage().getChatId().toString();

        List<SendMessage> nextPageFilm = HtmlDataParser.getTitles(linkData, "")
                .stream().map(f -> SendMessage.builder().chatId(chatId)
                        .text(f).build()).collect(Collectors.toList());

        nextPageFilm.add(SendMessage.builder().chatId(chatId).text("Navigate")
                .replyMarkup(keyboardFactory.getKeyboard(KeyboardType.PAGE_CONTROL).setKeyboard(linkData)).build());

        return nextPageFilm;
    }

    @Override
    public Callback getCorrectCallBack() {
        return Callback.PREVIOUS;
    }

}
