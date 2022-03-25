package com.foxminded.telebot.keyboard.service.implementation;

import com.foxminded.telebot.keyboard.service.KeyboardService;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class PageControlKeyboardAction implements KeyboardService {

    @Override
    public ReplyKeyboard setKeyboard(String setCallBack) {

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardButtons = new KeyboardRow();

        Stream.of("<-", " ", "->")
                .map(b -> KeyboardButton.builder().text(b).build()).forEach(keyboardButtons::add);


        keyboardRows.add(keyboardButtons);

        return ReplyKeyboardMarkup.builder().selective(true).keyboard(keyboardRows).build();
    }

    @Override
    public KeyboardType getType() {
        return KeyboardType.PAGE_CONTROL;
    }
}
