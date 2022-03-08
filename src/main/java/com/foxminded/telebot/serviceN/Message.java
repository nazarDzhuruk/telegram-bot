package com.foxminded.telebot.serviceN;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;
import java.util.List;

public interface Message {
    List<SendMessage> startMessage(String chatId);
    List<SendMessage> filmDescription(String message, String chatId) throws IOException;
    SendMessage helloMessage(String chatId, String nickname);
}
