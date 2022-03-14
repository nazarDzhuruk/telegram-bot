package com.foxminded.telebot.handler;

import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.callback.CallbackHandler;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;

public interface HandlerFactory {
    MessageHandler handleUpdate(MessageCommand messageCommand);
    CallbackHandler handleCallBack(Callback callback);
}
