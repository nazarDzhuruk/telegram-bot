package com.foxminded.telebot.handler.message;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class AboutHandlerAction implements MessageHandler {

    @Override
    public SendMessage handleMessage(Message message) {
        if (message.hasText()) {
            String username = message.getChat().getUserName();
            String chatId = message.getChatId().toString();
            return  SendMessage.builder().chatId(chatId).text("Hello " + username +
                    " I'm glad that you are interested in my project" +
                    " This telegram bot can send movies with descriptions." +
                    " The project is open source and you can use it as a base for your bot." +
                    " Here you can find code: https://github.com/nazarDzhuruk/telegram-bot").build();
        }
        else throw new UpdateHandlerException("No message");
    }

    @Override
    public Command getUniqueCommand() {
        return Command.ABOUT;
    }
}
