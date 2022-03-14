package com.foxminded.telebot.handler.message;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.keyboard.KeyboardFactory;
import com.foxminded.telebot.keyboard.KeyboardType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class GenresHandlerAction implements MessageHandler {

    private final KeyboardFactory keyboardFactory;

    public GenresHandlerAction(KeyboardFactory keyboardFactory) {
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        if (message.hasText()) {
            String chatId = message.getChatId().toString();
            return SendMessage.builder().chatId(chatId).text("Genres: ")
                    .replyMarkup(InlineKeyboardMarkup.builder()
                            .keyboard(keyboardFactory.getKeyboard(KeyboardType.GENRES)
                                    .getButtons("")).build()).build();
        } else throw new UpdateHandlerException("No message");
    }

    @Override
    public Command getUniqueCommand() {
        return Command.GENRES;
    }
}
