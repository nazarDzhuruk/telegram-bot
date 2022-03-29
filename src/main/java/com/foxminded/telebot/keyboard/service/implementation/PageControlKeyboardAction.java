package com.foxminded.telebot.keyboard.service.implementation;

import com.foxminded.telebot.keyboard.service.KeyboardService;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class PageControlKeyboardAction implements KeyboardService {
    private static final String LOG = "Keyboard service: Page Control – ";
    private static final String PREV = "previous";
    private static final String SPACE = " ";
    private static final String NEXT = "next";

    @Override
    public ReplyKeyboard setKeyboard(String setCallBack) {
        log.trace(LOG + "accessed");

        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();

        inlineButtons.add(Stream.of(PREV, SPACE, NEXT)
                .map(b -> InlineKeyboardButton.builder().text(b).callbackData(b + ":" + setCallBack).build())
                .collect(Collectors.toList()));

        log.info(LOG + setCallBack + " button has been created");

        return InlineKeyboardMarkup.builder().keyboard(inlineButtons).build();
    }

    @Override
    public KeyboardType getType() {
        return KeyboardType.PAGE_CONTROL;
    }

}
