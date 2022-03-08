package com.foxminded.telebot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class TelegramUser {
    @Id
    private Long id;
    private Long telegramId;
    private String nickname;

    public TelegramUser(){
        super();
    }

    public TelegramUser(long id, String nickname) {
        this();
        this.id = id;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelegramUser)) return false;
        TelegramUser that = (TelegramUser) o;
        return Objects.equals(id, that.id) && Objects.equals(telegramId, that.telegramId) && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telegramId, nickname);
    }

    @Override
    public String toString() {
        return "TelegramUser{" +
                "id=" + id +
                ", telegramId=" + telegramId +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
