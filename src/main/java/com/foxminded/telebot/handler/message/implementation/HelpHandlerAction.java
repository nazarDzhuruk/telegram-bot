package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.Command;
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
                    Command.START.getName() + SPACE + Command.START.getDescription() + "\r\n" +
                    "\n" +
                    Command.STOP.getName() + SPACE + Command.STOP.getDescription() + "\r\n" +
                    "\n" +
                    Command.ABOUT.getName() + SPACE + Command.ABOUT.getDescription() + "\r\n" +
                    "\n" +
                    Command.GENRES.getName() + SPACE + Command.GENRES.getDescription() + "\r\n" +
                    "\n" +
                    Command.HELP.getName() + SPACE + Command.HELP.getDescription()).build();
        } else throw new UpdateHandlerException("No message");
    }

    @Override
    public Command getUniqueCommand() {
        return Command.HELP;
    }
}
