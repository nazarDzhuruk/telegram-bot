package com.foxminded.telebot.keyboard.service.implementation;

import com.foxminded.telebot.keyboard.service.KeyboardService;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ShowKeyboardAction implements KeyboardService {
    private static final String KEYBOARD_BUTTON_TEXT = "SHOW";
    private static final String LOG = "Keyboard service: Show –  ";

    @Override
    public List<List<InlineKeyboardButton>> getButtons(String setCallBack) {
        log.trace(LOG + "accessed");

        List<List<InlineKeyboardButton>> showKeyboard = new ArrayList<>();
        List<InlineKeyboardButton> showButton = new ArrayList<>();

        showButton.add(InlineKeyboardButton.builder().text(KEYBOARD_BUTTON_TEXT).callbackData(setCallBack).build());
        showKeyboard.add(showButton);

        log.info(LOG + "buttons has been generated");
        return showKeyboard;
    }

    @Override
    public KeyboardType getType() {
        return KeyboardType.SHOW;
    }
}
