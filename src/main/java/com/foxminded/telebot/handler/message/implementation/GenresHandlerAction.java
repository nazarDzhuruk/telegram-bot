package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import com.foxminded.telebot.keyboard.KeyboardFactory;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class GenresHandlerAction implements MessageHandler {
    private static final String EXPECTED_EXCEPTION = "No message";
    private static final String TEXT_MESSAGE = "Film genres: ";
    private static final String GENRE = "genre:";
    private static final String LOG = "Message handler: Genres – ";
    private final KeyboardFactory keyboardFactory;

    public GenresHandlerAction(KeyboardFactory keyboardFactory) {
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        log.trace("accessed");

        if (message.hasText()) {
            log.info(LOG + "send message");

            String chatId = message.getChatId().toString();
            return SendMessage.builder().chatId(chatId).text(TEXT_MESSAGE)
                    .replyMarkup(keyboardFactory.getKeyboard(KeyboardType.GENRES).setKeyboard(GENRE)).build();
        } else {
            log.warn(LOG + "message not found; throws runtime exception");

            throw new UpdateHandlerException(EXPECTED_EXCEPTION);
        }
    }

    @Override
    public MessageCommand getUniqueCommand() {
        return MessageCommand.GENRES;
    }
}
