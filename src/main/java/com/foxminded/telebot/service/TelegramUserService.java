package com.foxminded.telebot.service;

import com.foxminded.telebot.model.TelegramUser;

import java.util.List;

public interface TelegramUserService {
    TelegramUser addUser(TelegramUser telegramUser);
    TelegramUser removeUserById(long userId);
    TelegramUser updateUser(TelegramUser telegramUser);
    List<TelegramUser> userList();
}
