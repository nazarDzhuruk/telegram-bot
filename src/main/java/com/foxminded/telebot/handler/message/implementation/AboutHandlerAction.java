package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class AboutHandlerAction implements MessageHandler {

    @Override
    public SendMessage handleMessage(Message message) {
        log.trace("Message handler: About – accessed");

        if (message.hasText()) {
            log.info("Message handler: About – send message");

            String username = message.getChat().getUserName();
            String chatId = message.getChatId().toString();
            return SendMessage.builder().chatId(chatId).text("Hello " + username +
                    " I'm glad that you are interested in my project" +
                    " This telegram bot can send movies with descriptions." +
                    " The project is open source and you can use it as a base for your bot." +
                    " Here you can find code: https://github.com/nazarDzhuruk/telegram-bot").build();
        } else {
            log.warn("Message handler: About – message not found; throws runtime exception");

            throw new UpdateHandlerException("No message");
        }
    }

    @Override
    public MessageCommand getUniqueCommand() {
        return MessageCommand.ABOUT;
    }
}
