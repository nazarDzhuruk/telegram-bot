package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.TelebotServiceException;
import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import com.foxminded.telebot.service.TelegramUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class StopHandlerAction implements MessageHandler {
    private static final String EXPECTED_EXCEPTION = "No message";
    private static final String GOODBYE_MESSAGE = "GOODBYE ";
    private static final String LOG = "Message handler: Stop – ";
    private final TelegramUserService userService;

    public StopHandlerAction(TelegramUserService userService) {
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
                log.info(LOG + "removing user from database");

                userService.removeUserById(Integer.parseInt(chatId));

            } catch (TelebotServiceException e) {
                log.warn(LOG + e.getMessage());

                throw new UpdateHandlerException(e.getMessage());
            }
            log.info(LOG + "send goodbye message");
            return SendMessage.builder().chatId(chatId).text(GOODBYE_MESSAGE + username).build();
        } else {
            log.warn(LOG + "message not found; throws exception");

            throw new UpdateHandlerException(EXPECTED_EXCEPTION);
        }
    }

    @Override
    public MessageCommand getUniqueCommand() {
        return MessageCommand.STOP;
    }
}
