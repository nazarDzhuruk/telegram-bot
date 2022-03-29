package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.exception.UpdateHandlerException;
import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class HelpHandlerActionTest {
    private MessageHandler underTest;

    @BeforeEach
    void setUp() {
        underTest = new HelpHandlerAction();
    }

    @Test
    void shouldReturnSupportedCommandsIfMessageExist() {

        Message message = Mockito.spy(new Message());

        doReturn(true).when(message).hasText();
        doReturn(123L).when(message).getChatId();

        assertThat(underTest.handleMessage(message).getText()).contains("Supported commands:");
    }

    @Test
    void shouldThrowAnExceptionIfMessageTextNotExist() {
        Message message = new Message();

        assertThatThrownBy(() -> underTest.handleMessage(message))
                .isInstanceOf(UpdateHandlerException.class);
    }
    @Test
    void shouldReturnProperUniqueCommand(){

        assertThat(underTest.getUniqueCommand()).isEqualTo(MessageCommand.HELP);
    }
}