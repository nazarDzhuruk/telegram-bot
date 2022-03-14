package com.foxminded.telebot.handler.callback.implementation;

import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.callback.CallbackHandler;
import com.foxminded.telebot.service.HtmlDataParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Component
public class HandleShowCallback implements CallbackHandler {

    private static final String LINK = "https://kinogo.film";

    @Override
    public List<SendMessage> getDescription(CallbackQuery callbackQuery) {
        return List.of(message(callbackQuery));
    }

    private SendMessage message(CallbackQuery callbackQuery) {
        String data = LINK + callbackQuery.getData() + ".html";
        String desc = HtmlDataParser.getFilmDesc(data);
        return SendMessage.builder().chatId(callbackQuery.getMessage().getChatId().toString()).text(desc).build();
    }

    @Override
    public Callback getCorrectCallBack() {
        return Callback.SHOW;
    }
}