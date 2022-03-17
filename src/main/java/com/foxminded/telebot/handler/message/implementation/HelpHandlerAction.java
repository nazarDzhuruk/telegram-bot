package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class HelpHandlerAction implements MessageHandler {
    private static final String COLON_SPACE = ": ";

    @Override
    public SendMessage handleMessage(Message message) {
        log.trace("Message handler: Help – accessed");

        if (message.hasText()) {
            log.info("Message handler: Help – send message");

            String chatId = message.getChatId().toString();
            return SendMessage.builder().chatId(chatId).text("Supported commands:" + "\r\n" +
                    "\n" +
                    MessageCommand.START.getName() + COLON_SPACE + MessageCommand.START.getDescription() + "\r\n" +
                    "\n" +
                    MessageCommand.STOP.getName() + COLON_SPACE + MessageCommand.STOP.getDescription() + "\r\n" +
                    "\n" +
                    MessageCommand.ABOUT.getName() + COLON_SPACE + MessageCommand.ABOUT.getDescription() + "\r\n" +
                    "\n" +
                    MessageCommand.GENRES.getName() + COLON_SPACE + MessageCommand.GENRES.getDescription() + "\r\n" +
                    "\n" +
                    MessageCommand.HELP.getName() + COLON_SPACE + MessageCommand.HELP.getDescription()).build();
        } else {
            log.warn("Message handler: Help – message not found; throws runtime exception");

            throw new UpdateHandlerException("No message");
        }
    }

    @Override
    public MessageCommand getUniqueCommand() {
        return MessageCommand.HELP;
    }
}
