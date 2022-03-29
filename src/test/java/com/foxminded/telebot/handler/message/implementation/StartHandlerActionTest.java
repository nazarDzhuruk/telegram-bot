package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.TelebotServiceException;
import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import com.foxminded.telebot.model.TelegramUser;
import com.foxminded.telebot.service.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartHandlerActionTest {

    private MessageHandler underTest;

    @Mock
    private TelegramUserService telegramUserService;

    @BeforeEach
    void setUp() {
        underTest = new StartHandlerAction(telegramUserService);
    }

    @Test
    void shouldRegisterNewUserAndSendHello() {
        TelegramUser telegramUser = new TelegramUser(123, "test");

        Message message = Mockito.spy(new Message());
        Chat chat = Mockito.spy(new Chat());

        doReturn(true).when(message).hasText();

        doReturn(chat).when(message).getChat();

        doReturn("test").when(chat).getUserName();
        doReturn(123L).when(chat).getId();

        underTest.handleMessage(message);

        ArgumentCaptor<TelegramUser> argumentCaptor = ArgumentCaptor.forClass(TelegramUser.class);
        verify(telegramUserService).addUser(argumentCaptor.capture());
        TelegramUser user = argumentCaptor.getValue();

        assertThat(telegramUser).isEqualTo(user);

        assertThat(underTest.handleMessage(message).getText())
                .isEqualTo("HELLO " + telegramUser.getNickname());
    }

    @Test
    void shouldSendWelcomeBackMessage() {
        TelegramUser telegramUser = new TelegramUser(123, "test");
        Message message = Mockito.spy(new Message());
        Chat chat = Mockito.spy(new Chat());

        doReturn(true).when(message).hasText();

        doReturn(chat).when(message).getChat();

        doReturn(telegramUser.getNickname()).when(chat).getUserName();
        doReturn(123L).when(chat).getId();

        given(telegramUserService.addUser(telegramUser)).willThrow(new TelebotServiceException("user exist"));

        assertThat(underTest.handleMessage(message).getText())
                .isEqualTo("WELCOME BACK " + telegramUser.getNickname());
    }

    @Test
    void shouldThrowAnExceptionIfMessageTextNotExist() {
        Message message = new Message();

        assertThatThrownBy(() -> underTest.handleMessage(message)).isInstanceOf(UpdateHandlerException.class);
    }

    @Test
    void shouldReturnProperUniqueCommand() {
        assertThat(underTest.getUniqueCommand()).isEqualTo(MessageCommand.START);
    }
}