package com.foxminded.telebot.service;

import com.foxminded.telebot.model.TelegramUser;

import java.util.List;

public interface TelegramUserService {
    TelegramUser addUser(TelegramUser telegramUser);
    TelegramUser removeUserById(int userId);
    TelegramUser updateUser(TelegramUser telegramUser);
    TelegramUser findUserByChatId(int userId);
    List<TelegramUser> userList();
}
