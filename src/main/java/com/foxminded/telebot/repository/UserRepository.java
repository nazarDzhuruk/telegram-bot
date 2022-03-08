package com.foxminded.telebot.repository;

import com.foxminded.telebot.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<TelegramUser, Long> {
}
