package com.foxminded.telebot.handler;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.MessageHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class HandlerFactoryAction implements HandlerFactory {

    private List<MessageHandler> messageHandlerList;

    private Map<Command, MessageHandler> messageHandlerMap;

    public HandlerFactoryAction(List<MessageHandler> messageHandlerList) {
        this.messageHandlerList = messageHandlerList;
    }

    @PostConstruct
    private void init() {
        messageHandlerMap = new HashMap<>();
        messageHandlerList.forEach(m -> messageHandlerMap.put(m.getUniqueCommand(), m));
    }

    @Override
    public MessageHandler handleUpdate(Command command) {
        return Optional.ofNullable(messageHandlerMap.get(command))
                .orElseThrow(() -> new UpdateHandlerException("Unrecognizable command"));
    }
}
