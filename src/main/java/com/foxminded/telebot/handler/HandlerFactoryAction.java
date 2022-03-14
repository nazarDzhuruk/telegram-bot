package com.foxminded.telebot.handler;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.callback.CallbackHandler;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class HandlerFactoryAction implements HandlerFactory {
    private static final String EXPECTED_EXCEPTION = "Unrecognizable command";

    private final List<MessageHandler> messageHandlerList;
    private final List<CallbackHandler> callbackHandlerList;

    private Map<MessageCommand, MessageHandler> messageHandlerMap;
    private Map<Callback, CallbackHandler> callbackHandlerMap;

    public HandlerFactoryAction(List<MessageHandler> messageHandlerList, List<CallbackHandler> callbackHandlerList) {
        this.messageHandlerList = messageHandlerList;
        this.callbackHandlerList = callbackHandlerList;

    }

    @PostConstruct
    private void init() {
        messageHandlerMap = new HashMap<>();
        callbackHandlerMap = new HashMap<>();

        messageHandlerList.forEach(m -> messageHandlerMap.put(m.getUniqueCommand(), m));
        callbackHandlerList.forEach(c -> callbackHandlerMap.put(c.getCorrectCallBack(), c));
    }

    @Override
    public MessageHandler handleUpdate(MessageCommand messageCommand) {
        return Optional.ofNullable(messageHandlerMap.get(messageCommand))
                .orElseThrow(() -> new UpdateHandlerException(EXPECTED_EXCEPTION));
    }

    @Override
    public CallbackHandler handleCallBack(Callback callback) {
        return Optional.ofNullable(callbackHandlerMap.get(callback))
                .orElseThrow(() -> new UpdateHandlerException(EXPECTED_EXCEPTION));
    }
}