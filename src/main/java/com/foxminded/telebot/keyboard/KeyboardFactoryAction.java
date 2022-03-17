package com.foxminded.telebot.keyboard;

import com.foxminded.telebot.keyboard.service.KeyboardService;
import com.foxminded.telebot.keyboard.service.KeyboardType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class KeyboardFactoryAction implements KeyboardFactory {
    private final List<KeyboardService> keyboardServiceList;
    private static final String LOG = "Keyboard factory: ";

    private Map<KeyboardType, KeyboardService> keyboardServiceMap;

    public KeyboardFactoryAction(List<KeyboardService> keyboardServiceList) {
        this.keyboardServiceList = keyboardServiceList;
    }

    @PostConstruct
    void init() {
        log.trace(LOG + "initializing");
        keyboardServiceMap = new HashMap<>();
        keyboardServiceList.forEach(k -> keyboardServiceMap.put(k.getType(), k));
        log.info(LOG + "init successful");
    }

    @Override
    public KeyboardService getKeyboard(KeyboardType type) {
        log.info(LOG + "return proper keyboard");
        return Optional.ofNullable(keyboardServiceMap.get(type)).orElseThrow();
    }
}
