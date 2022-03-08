package com.foxminded.telebot.service.implemantation;

import com.foxminded.telebot.exception.TelebotServiceException;
import com.foxminded.telebot.model.TelegramUser;
import com.foxminded.telebot.repository.UserRepository;
import com.foxminded.telebot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramUserAction implements TelegramUserService {
    private static final String EXPECTED_EXCEPTION = "User not exist.";
    private final UserRepository repository;

    @Autowired
    public TelegramUserAction(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public TelegramUser addUser(TelegramUser telegramUser) {
        repository.save(telegramUser);
        return telegramUser;
    }

    @Override
    public TelegramUser removeUserById(long userId) {
        TelegramUser telegramUser = repository.findById(userId)
                .orElseThrow(() -> new TelebotServiceException(EXPECTED_EXCEPTION));
        repository.delete(telegramUser);
        return telegramUser;
    }

    @Override
    public TelegramUser updateUser(TelegramUser telegramUser) {
        TelegramUser telegramUserFromDatabase = repository
                .findById(telegramUser.getId()).orElseThrow(() -> new TelebotServiceException(EXPECTED_EXCEPTION));
        telegramUserFromDatabase.setNickname(telegramUser.getNickname());
        repository.save(telegramUserFromDatabase);
        return telegramUserFromDatabase;
    }

    @Override
    public List<TelegramUser> userList() {
        return repository.findAll();
    }
}
