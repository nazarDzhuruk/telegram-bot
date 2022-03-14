package com.foxminded.telebot.configuration;

import com.foxminded.telebot.exception.TelebotWizzardException;
import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.HandlerFactory;
import com.foxminded.telebot.service.GenreService;
import com.foxminded.telebot.service.HtmlDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
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
        } catch (TelegramApiException e) {
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
                Message message = update.getMessage();
                String command = message.getText().replace("/", "");
                execute(handleMessage.handleUpdate(MessageCommand.valueOf(command.toUpperCase(Locale.ROOT)))
                        .handleMessage(message));
            } catch (TelegramApiException e) {
                throw new TelebotWizzardException(e.getMessage());
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            boolean one = genreService.getAll().stream().anyMatch(g -> g.getGenreName().equals(callbackData));

            if (one) {
                handleMessage.handleCallBack(Callback.GENRES)
                        .getDescription(update.getCallbackQuery()).forEach(s -> {
                            try {
                                execute(s);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        });
            }
            else {
                handleMessage.handleCallBack(Callback.SHOW)
                        .getDescription(update.getCallbackQuery()).forEach(s -> {
                            try {
                                execute(s);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        });
            }
        }
    }
}

