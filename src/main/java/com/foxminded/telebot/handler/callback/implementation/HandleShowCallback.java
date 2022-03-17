package com.foxminded.telebot.handler.callback.implementation;

import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.callback.CallbackHandler;
import com.foxminded.telebot.model.StaticLink;
import com.foxminded.telebot.service.HtmlDataParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Slf4j
@Component
public class HandleShowCallback implements CallbackHandler {
    private static final String LOG = "Callback handle: Show call – ";

    @Override
    public List<SendMessage> getDescription(CallbackQuery callbackQuery) {
        log.info(LOG + "data generated");
        return List.of(message(callbackQuery));
    }

    private SendMessage message(CallbackQuery callbackQuery) {
        log.trace(LOG + "accessed");
        String data = StaticLink.MAIN_LINK + callbackQuery.getData().split(":")[1] + ".html";
        String desc = HtmlDataParser.getFilmDesc(data);
        return SendMessage.builder().chatId(callbackQuery.getMessage().getChatId().toString()).text(desc).build();
    }

    @Override
    public Callback getCorrectCallBack() {
        return Callback.SHOW;
    }
}