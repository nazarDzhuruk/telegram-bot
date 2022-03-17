package com.foxminded.telebot.handler;

import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.message.MessageCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Locale;

@Component
public class HandlerUtilImpl implements HandlerUtil {
    private final HandlerFactory handlerFactory;

    public HandlerUtilImpl(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @Override
    public List<SendMessage> sendMessage(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            String command = update.getMessage().getText().replace("/", "").toUpperCase(Locale.ROOT);
            return List.of(handlerFactory.handleUpdate(MessageCommand.valueOf(command))
                    .handleMessage(update.getMessage()));
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData().split(":")[0].toUpperCase(Locale.ROOT);
            return handlerFactory.handleCallBack(Callback.valueOf(callbackData))
                    .getDescription(update.getCallbackQuery());
        }
        return null;
    }
}
