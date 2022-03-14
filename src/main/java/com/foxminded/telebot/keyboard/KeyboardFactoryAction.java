package com.foxminded.telebot.keyboard;

import com.foxminded.telebot.keyboard.service.KeyboardService;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class KeyboardFactoryAction implements KeyboardFactory {
    private final List<KeyboardService> keyboardServiceList;

    private Map<KeyboardType, KeyboardService> keyboardServiceMap;

    public KeyboardFactoryAction(List<KeyboardService> keyboardServiceList) {
        this.keyboardServiceList = keyboardServiceList;
    }

    @PostConstruct
    void init() {
        keyboardServiceMap = new HashMap<>();
        keyboardServiceList.forEach(k -> keyboardServiceMap.put(k.getType(), k));
    }

    @Override
    public KeyboardService getKeyboard(KeyboardType type) {
        return Optional.ofNullable(keyboardServiceMap.get(type)).orElseThrow();
    }
}
