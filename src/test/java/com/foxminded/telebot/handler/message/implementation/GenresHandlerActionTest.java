package com.foxminded.telebot.handler.message.implementation;

import com.foxminded.telebot.handler.message.MessageCommand;
import com.foxminded.telebot.handler.message.MessageHandler;
import com.foxminded.telebot.keyboard.KeyboardFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GenresHandlerActionTest {
    private MessageHandler underTest;

    @Mock
    private KeyboardFactory keyboardFactory;

    @BeforeEach
    void setUp() {
        underTest = new GenresHandlerAction(keyboardFactory);
    }

    @Test
    void shouldReturnProperUniqueCommand(){
        assertThat(underTest.getUniqueCommand()).isEqualTo(MessageCommand.GENRES);
    }
}