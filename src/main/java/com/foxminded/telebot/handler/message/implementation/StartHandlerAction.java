package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.TelebotServiceException;
import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import com.foxminded.telebot.model.TelegramUser;
import com.foxminded.telebot.service.TelegramUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class StartHandlerAction implements MessageHandler {
    private static final String EXPECTED_EXCEPTION = "No message";
    private static final String WELCOME_MESSAGE = "WELCOME BACK ";
    private static final String HELLO_MESSAGE = "HELLO ";
    private static final String LOG = "Message handler: Start – ";
    private final TelegramUserService userService;

    @Autowired
    public StartHandlerAction(TelegramUserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        log.trace(LOG + "accessed");

        if (message.hasText()) {

            Chat chat = message.getChat();

            String username = chat.getUserName();
            String chatId = chat.getId().toString();

            try {
                userService.addUser(new TelegramUser(Integer.parseInt(chatId), username));

                log.info(LOG + "register new user");
            } catch (TelebotServiceException e) {
                log.info(LOG + e.getMessage());

                log.info(LOG + "send welcome back message");
                return SendMessage.builder().text(WELCOME_MESSAGE + username).chatId(chatId).build();
            }
            log.info(LOG + "send hello message for new user");
            return SendMessage.builder().text(HELLO_MESSAGE + username).chatId(chatId).build();

        } else {
            log.warn(LOG + "message not found; throws exception");

            throw new UpdateHandlerException(EXPECTED_EXCEPTION);
        }
    }

    @Override
    public MessageCommand getUniqueCommand() {
        return MessageCommand.START;
    }

}
