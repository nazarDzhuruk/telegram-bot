package com.foxminded.telebot.service.implemantation;

import com.foxminded.telebot.exception.TelebotServiceException;
import com.foxminded.telebot.model.TelegramUser;
import com.foxminded.telebot.repository.UserRepository;
import com.foxminded.telebot.service.TelegramUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TelegramUserAction implements TelegramUserService {
    private static final String EXPECTED_EXCEPTION = "User not exist.";
    private static final String LOG = "Telegram user service: ";
    private final UserRepository repository;

    @Autowired
    public TelegramUserAction(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public TelegramUser addUser(TelegramUser telegramUser) {
        log.trace(LOG + "add user method accessed");
        if (repository.findById(telegramUser.getId()).isEmpty()) {
            repository.save(telegramUser);
            log.info(LOG + "user added successfully");
        } else {
            log.warn(LOG + "add user method – " + EXPECTED_EXCEPTION);
            throw new TelebotServiceException("User exist !");
        }
        return telegramUser;
    }

    @Override
    public TelegramUser removeUserById(int userId) {
        log.trace(LOG + "remove user by id method accessed");
        TelegramUser telegramUser = repository.findById(userId)
                .orElseThrow(() -> {
                    log.warn(LOG + "remove user by id – " + EXPECTED_EXCEPTION);
                    return new TelebotServiceException(EXPECTED_EXCEPTION);
                });
        repository.delete(telegramUser);
        log.info(LOG + "user has been deleted successfully");
        return telegramUser;
    }

    @Override
    public TelegramUser updateUser(TelegramUser telegramUser) {
        log.trace(LOG + "update user method accessed");
        TelegramUser telegramUserFromDatabase = repository
                .findById(telegramUser.getId()).orElseThrow(() -> {
                    log.warn(LOG + "update user – " + EXPECTED_EXCEPTION);
                    return new TelebotServiceException(EXPECTED_EXCEPTION);
                });
        telegramUserFromDatabase.setNickname(telegramUser.getNickname());
        repository.save(telegramUserFromDatabase);
        log.info(LOG + "user data has been updated successfully");
        return telegramUserFromDatabase;
    }

    @Override
    public TelegramUser findUserByChatId(int userId) {
        log.trace(LOG + "find user by id method accessed");
        return repository.findById(userId)
                .orElseThrow(() -> {
                    log.warn(LOG + "find user by id – " + EXPECTED_EXCEPTION);
                    return new TelebotServiceException(EXPECTED_EXCEPTION);
                });
    }

    @Override
    public List<TelegramUser> userList() {
        log.trace(LOG + "user list method accessed");
        return repository.findAll();
    }
}
