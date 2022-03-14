package com.foxminded.telebot.keyboard.service.implementation;

import com.foxminded.telebot.keyboard.service.KeyboardService;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShowKeyboardAction implements KeyboardService {

    @Override
    public List<List<InlineKeyboardButton>> getButtons(String setCallBack) {

        List<List<InlineKeyboardButton>> showKeyboard = new ArrayList<>();
        List<InlineKeyboardButton> showButton = new ArrayList<>();

        showButton.add(InlineKeyboardButton.builder().text("show").callbackData(setCallBack).build());
        showKeyboard.add(showButton);
        return showKeyboard;
    }

    @Override
    public KeyboardType getType() {
        return KeyboardType.SHOW;
    }
}
