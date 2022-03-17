package com.foxminded.telebot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface HandlerUtil {
    List<SendMessage> sendMessage(Update update);
}
