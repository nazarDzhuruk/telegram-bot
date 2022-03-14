package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.TelebotServiceException;
import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.Command;
import com.foxminded.telebot.handler.message.MessageHandler;
import com.foxminded.telebot.model.TelegramUser;
import com.foxminded.telebot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartHandlerAction implements MessageHandler {

    private final TelegramUserService userService;

    @Autowired
    public StartHandlerAction(TelegramUserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        if (message.hasText()) {
            String username = message.getChat().getUserName();
            String chatId = message.getChatId().toString();
            try {
                userService.addUser(new TelegramUser(Integer.parseInt(chatId), username));
            } catch (TelebotServiceException e) {
                return SendMessage.builder().text("Welcome back " + username).chatId(chatId).build();
            }
            return SendMessage.builder().text("Hello " + username).chatId(chatId).build();
        } else throw new UpdateHandlerException("No message");
    }

    @Override
    public Command getUniqueCommand() {
        return Command.START;
    }

}
