package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AboutHandlerActionTest {
    private MessageHandler underTest;

    @BeforeEach
    void setUp() {
        underTest = new AboutHandlerAction();
    }

    @Test
    void shouldSendAboutMessage() {
        Message message = Mockito.spy(new Message());
        Chat chat = Mockito.spy(new Chat());

        doReturn(true).when(message).hasText();
        doReturn(chat).when(message).getChat();
        doReturn("test").when(chat).getUserName();
        doReturn(123L).when(chat).getId();

        assertThat(underTest.handleMessage(message).getText()).contains("Hello " + chat.getUserName() +
                " I'm glad that you are interested in my project" +
                " This telegram bot can send movies with descriptions." +
                " The project is open source and you can use it as a base for your bot." +
                " Here you can find code: https://github.com/nazarDzhuruk/telegram-bot");

    }

    @Test
    void shouldReturnProperUniqueCommand() {
        assertThat(underTest.getUniqueCommand()).isEqualTo(MessageCommand.ABOUT);
    }
}