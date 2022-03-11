package com.foxminded.telebot.configuration;

import com.foxminded.telebot.exception.TelebotWizzardException;
import com.foxminded.telebot.handler.Command;
import com.foxminded.telebot.handler.HandlerFactory;
import com.foxminded.telebot.service.GenreService;
import com.foxminded.telebot.service.HtmlDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class TelebotWizzard extends TelegramLongPollingBot {
    private final BotConfiguration configuration;
    private final GenreService genreService;
    private final HandlerFactory handleMessage;

    @Autowired
    public TelebotWizzard(HandlerFactory handleMessage, BotConfiguration configuration, GenreService genreService) {
        this.configuration = configuration;
        this.genreService = genreService;
        this.handleMessage = handleMessage;
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
            try {
                execute(handleMessage.handleUpdate(Command.valueOf(update.getMessage()
                                .getText().toUpperCase(Locale.ROOT)))
                        .handleMessage(update.getMessage()));
            } catch (TelegramApiException e) {
                throw new TelebotWizzardException(e.getMessage());
            }
        }
    }
}
