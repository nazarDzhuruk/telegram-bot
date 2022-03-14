package com.foxminded.telebot.handler.callback;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

public interface CallbackHandler {
    List<SendMessage> getDescription(CallbackQuery callbackQuery);
    Callback getCorrectCallBack();
}
