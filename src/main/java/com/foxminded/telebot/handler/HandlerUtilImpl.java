package com.foxminded.telebot.handler;

import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.message.MessageCommand;

import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class HandlerUtilImpl implements HandlerUtil {
    private static final String LOG = "Handler util: Handle ";
    private final HandlerFactory handlerFactory;

    public HandlerUtilImpl(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @Override
    public List<SendMessage> sendMessage(Update update) {
        log.trace(LOG + "accessed");
        if (update.getMessage() != null && update.getMessage().hasText()) {
            log.info(LOG + "message; text exist; checking command");
            String command = update.getMessage().getText().replace("/", "").toUpperCase(Locale.ROOT);
            return List.of(handlerFactory.handleUpdate(MessageCommand.valueOf(command))
                    .handleMessage(update.getMessage()));
        } else if (update.hasCallbackQuery() && !update.getCallbackQuery().getData().isEmpty()) {
            String callbackData = update.getCallbackQuery().getData().split(":")[0].toUpperCase(Locale.ROOT);
            log.info(LOG + "callback; checking data");
            return handlerFactory.handleCallBack(Callback.valueOf(callbackData))
                    .getDescription(update.getCallbackQuery());
        }
        return Collections.emptyList();
    }
}
