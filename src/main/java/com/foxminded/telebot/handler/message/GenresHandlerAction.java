package com.foxminded.telebot.handler.message;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.Command;
import com.foxminded.telebot.keyboard.KeyboardService;
import com.foxminded.telebot.service.GenreService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class GenresHandlerAction implements MessageHandler {
    private final GenreService genreService;
    private final KeyboardService keyboardService;

    public GenresHandlerAction(GenreService genreService, KeyboardService keyboardService) {
        this.genreService = genreService;
        this.keyboardService = keyboardService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        if (message.hasText()) {
            String chatId = message.getChatId().toString();
            return SendMessage.builder().chatId(chatId).text("Genres: ")
                    .replyMarkup(InlineKeyboardMarkup.builder()
                            .keyboard(keyboardService.genreButtons(genreService.getAll())).build()).build();
        } else throw new UpdateHandlerException("No message");
    }

    @Override
    public Command getUniqueCommand() {
        return Command.GENRES;
    }
}
