package com.foxminded.telebot.handler.message.implementation;

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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StopHandlerActionTest {
    private MessageHandler underTest;

    @Mock
    private TelegramUserService telegramUserService;

    @BeforeEach
    void setUp() {
        underTest = new StopHandlerAction(telegramUserService);
    }

    @Test
    void shouldDeleteUserAndSayGoodbye() {
        TelegramUser telegramUser = new TelegramUser(123, "test");

        Message message = Mockito.spy(new Message());
        Chat chat = Mockito.spy(new Chat());

        doReturn(true).when(message).hasText();

        doReturn(chat).when(message).getChat();

        doReturn("test").when(chat).getUserName();
        doReturn(123L).when(chat).getId();

        underTest.handleMessage(message);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(telegramUserService).removeUserById(argumentCaptor.capture());

        assertThat(telegramUser.getId()).isEqualTo(argumentCaptor.getValue());

        assertThat(underTest.handleMessage(message).getText())
                .isEqualTo("GOODBYE " + telegramUser.getNickname());
    }

    @Test
    void shouldThrowAnExceptionIfMessageTextNotExist() {
        Message message = new Message();

        assertThatThrownBy(() -> underTest.handleMessage(message)).isInstanceOf(UpdateHandlerException.class);
    }
    @Test
    void shouldReturnProperUniqueCommand(){
        assertThat(underTest.getUniqueCommand()).isEqualTo(MessageCommand.STOP);
    }
}