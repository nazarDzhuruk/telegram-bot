package com.foxminded.telebot.handler;

import com.foxminded.telebot.handler.Command;
import com.foxminded.telebot.handler.message.MessageHandler;

public interface HandlerFactory {
    MessageHandler handleUpdate(Command command);
}
