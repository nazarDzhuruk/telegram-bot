package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class AboutHandlerAction implements MessageHandler {
    private static final String LOG = "Message handler: About – ";

    @Override
    public SendMessage handleMessage(Message message) {
        log.trace(LOG + "accessed");

        if (message.hasText()) {
            log.info(LOG + "send message");
            Chat chat = message.getChat();

            String username = chat.getUserName();
            String chatId = chat.getId().toString();

            return SendMessage.builder().chatId(chatId).text("Hello " + username +
                    " I'm glad that you are interested in my project" +
                    " This telegram bot can send movies with descriptions." +
                    " The project is open source and you can use it as a base for your bot." +
                    " Here you can find code: https://github.com/nazarDzhuruk/telegram-bot").build();
        } else {
            log.warn(LOG + "message not found; throws runtime exception");

            throw new UpdateHandlerException("No message");
        }
    }

    @Override
    public MessageCommand getUniqueCommand() {
        return MessageCommand.ABOUT;
    }
}
