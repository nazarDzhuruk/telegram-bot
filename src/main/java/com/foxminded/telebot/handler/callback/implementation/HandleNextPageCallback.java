package com.foxminded.telebot.handler.callback.implementation;

import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.callback.CallbackHandler;
import com.foxminded.telebot.handler.callback.implementation.helper.Browser;
import com.foxminded.telebot.keyboard.KeyboardFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Component
public class HandleNextPageCallback implements CallbackHandler {
    private static final String SHOW = "show:";
    private static final String PAGE = "page/";
    private static final String COLON = ":";
    private final KeyboardFactory keyboardFactory;

    public HandleNextPageCallback(KeyboardFactory keyboardFactory) {
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public List<SendMessage> getDescription(CallbackQuery callbackQuery) {
        String linkData;
        int number;

        String link = callbackQuery.getData().split(COLON)[1];
        String[] page = link.split(PAGE);

        try {
            number = Integer.parseInt(page[1]) + 1;
            linkData = page[0] + PAGE + number;
        } catch (IndexOutOfBoundsException r) {
            number = 2;
            linkData = link + PAGE + number;
        }

        String chatId = callbackQuery.getMessage().getChatId().toString();

        return Browser.browsePage(chatId, linkData, keyboardFactory);
    }

    @Override
    public Callback getCorrectCallBack() {
        return Callback.NEXT;
    }
}
