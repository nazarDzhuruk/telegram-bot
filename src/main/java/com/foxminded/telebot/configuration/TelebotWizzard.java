package com.foxminded.telebot.configuration;

import com.foxminded.telebot.exception.TelebotWizzardException;
import com.foxminded.telebot.handler.HandlerUtil;
import com.foxminded.telebot.service.GenreService;
import com.foxminded.telebot.service.HtmlDataParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TelebotWizzard extends TelegramLongPollingBot {
    private static final String LOG = "Telebot wizzard: ";
    private final BotConfiguration configuration;
    private final GenreService genreService;
    private final HandlerUtil handlerUtil;


    @Autowired
    public TelebotWizzard(HandlerUtil handlerUtil, BotConfiguration configuration, GenreService genreService) {
        this.configuration = configuration;
        this.genreService = genreService;
        this.handlerUtil = handlerUtil;
    }

    @PostConstruct
    public void init() {
        log.trace(LOG + "initializing");
        HtmlDataParser.getGenres().forEach(genreService::addGenre);
        try {
            log.info(LOG + "registering bot");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            String msg = e.getMessage();
            log.warn(LOG + msg);
            throw new TelebotWizzardException(msg);
        }
    }

    @Override
    public String getBotUsername() {
        log.trace(LOG + "getting bot name");
        return configuration.getBotUserName();
    }

    @Override
    public String getBotToken() {
        log.trace(LOG + "getting bot token");
        return configuration.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.trace(LOG + "received update");
        handlerUtil.sendMessage(update).forEach(message -> {
            try {
                log.info(LOG + "message executing");
                execute(message);
            } catch (TelegramApiException e) {
                log.warn(LOG + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}