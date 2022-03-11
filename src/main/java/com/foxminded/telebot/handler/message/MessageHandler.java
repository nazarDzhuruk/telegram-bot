package com.foxminded.telebot.handler.message;

import com.foxminded.telebot.handler.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {
    SendMessage handleMessage(Message message);
    Command getUniqueCommand();
}
