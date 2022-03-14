package com.foxminded.telebot.handler.message;

import com.foxminded.telebot.exception.TelebotServiceException;
import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.service.TelegramUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StopHandlerAction implements MessageHandler {
    private final TelegramUserService userService;

    public StopHandlerAction(TelegramUserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        if (message.hasText()) {
            String username = message.getChat().getUserName();
            String chatId = message.getChatId().toString();

            try {
                userService.removeUserById(Integer.parseInt(chatId));
            } catch (TelebotServiceException e) {
                throw new UpdateHandlerException(e.getMessage());
            }
            return SendMessage.builder().chatId(chatId).text(username + " Goodbye !").build();
        } else throw new UpdateHandlerException("No message");
    }

    @Override
    public Command getUniqueCommand() {
        return Command.STOP;
    }
}
