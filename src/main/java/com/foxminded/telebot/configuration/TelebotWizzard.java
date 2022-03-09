package com.foxminded.telebot.configuration;

import com.foxminded.telebot.exception.TelebotWizzardException;
import com.foxminded.telebot.keyboard.KeyboardService;
import com.foxminded.telebot.repository.GenreRepository;
import com.foxminded.telebot.service.GenreService;
import com.foxminded.telebot.service.HtmlDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@Component
public class TelebotWizzard extends TelegramLongPollingBot {
    private final BotConfiguration configuration;
    private final GenreService genreService;
    private final KeyboardService keyboardService;


    @Autowired
    public TelebotWizzard(BotConfiguration configuration, GenreService genreService, KeyboardService keyboardService) {
        this.configuration = configuration;
        this.genreService = genreService;
        this.keyboardService = keyboardService;
    }

    @PostConstruct
    public void init() {
        HtmlDataParser.getGenres().forEach(genreService::addGenre);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (Exception e) {
            throw new TelebotWizzardException(e.getMessage());
        }

    }

    @Override
    public String getBotUsername() {
        return configuration.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return configuration.getBotToken();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getUserName();

            try {
                execute(SendMessage.builder().chatId(String.valueOf(chatId))
                        .text("hi " + userName)
                        .replyMarkup(InlineKeyboardMarkup.builder()
                                .keyboard(keyboardService.genreButtons(genreService.getAll())).build()).build());
            } catch (TelegramApiException e) {
                throw new TelebotWizzardException(e.getMessage());
            }
        }
    }
}
