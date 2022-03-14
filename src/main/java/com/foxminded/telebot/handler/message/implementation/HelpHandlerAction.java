package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HelpHandlerAction implements MessageHandler {
    private static final String SPACE = ": ";

    @Override
    public SendMessage handleMessage(Message message) {
        if (message.hasText()) {
            String chatId = message.getChatId().toString();
            return SendMessage.builder().chatId(chatId).text("Supported commands:" + "\r\n" +
                    "\n" +
                    MessageCommand.START.getName() + SPACE + MessageCommand.START.getDescription() + "\r\n" +
                    "\n" +
                    MessageCommand.STOP.getName() + SPACE + MessageCommand.STOP.getDescription() + "\r\n" +
                    "\n" +
                    MessageCommand.ABOUT.getName() + SPACE + MessageCommand.ABOUT.getDescription() + "\r\n" +
                    "\n" +
                    MessageCommand.GENRES.getName() + SPACE + MessageCommand.GENRES.getDescription() + "\r\n" +
                    "\n" +
                    MessageCommand.HELP.getName() + SPACE + MessageCommand.HELP.getDescription()).build();
        } else throw new UpdateHandlerException("No message");
    }

    @Override
    public MessageCommand getUniqueCommand() {
        return MessageCommand.HELP;
    }
}
