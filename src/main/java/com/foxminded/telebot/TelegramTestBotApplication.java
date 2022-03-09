package com.foxminded.telebot;

import com.foxminded.telebot.model.Genre;
import com.foxminded.telebot.service.GenreService;
import com.foxminded.telebot.service.HtmlDataParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class TelegramTestBotApplication {
    public static GenreService genreService;

    public TelegramTestBotApplication(GenreService genreService) {
        this.genreService = genreService;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TelegramTestBotApplication.class);

    }
}
