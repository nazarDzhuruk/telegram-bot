package com.foxminded.telebot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class TelegramUser {
    @Id
    private Integer id;
    private String nickname;

    protected TelegramUser(){}

    public TelegramUser(Integer id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        TelegramUser user = (TelegramUser) o;
        return Objects.equals(id, user.id) && Objects.equals(nickname, user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname);
    }

    @Override
    public String toString() {
        return "TelegramUser{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
