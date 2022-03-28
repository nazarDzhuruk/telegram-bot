package com.foxminded.telebot.handler;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.callback.Callback;
import com.foxminded.telebot.handler.callback.CallbackHandler;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Component
public class HandlerFactoryAction implements HandlerFactory {
    private static final String EXPECTED_EXCEPTION = "Unrecognizable command";
    private static final String LOG = "Update handlers factory:  ";

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
        log.trace(LOG + "initializing");
        messageHandlerMap = new EnumMap<>(MessageCommand.class);
        callbackHandlerMap = new EnumMap<>(Callback.class);

        messageHandlerList.forEach(m -> messageHandlerMap.put(m.getUniqueCommand(), m));
        callbackHandlerList.forEach(c -> callbackHandlerMap.put(c.getCorrectCallBack(), c));
        log.info(LOG + "init successful");
    }

    @Override
    public MessageHandler handleUpdate(MessageCommand messageCommand) {
        log.info(LOG + "return proper MessageHandler");
        return Optional.ofNullable(messageHandlerMap.get(messageCommand))
                .orElseThrow(() -> new UpdateHandlerException(EXPECTED_EXCEPTION));
    }

    @Override
    public CallbackHandler handleCallBack(Callback callback) {
        log.info(LOG + "return proper CallbackHandler");
        return Optional.ofNullable(callbackHandlerMap.get(callback))
                .orElseThrow(() -> new UpdateHandlerException(EXPECTED_EXCEPTION));
    }
}