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

        Document document = Jsoup.connect("https://kinogo.film/").get();
        Elements element = document.getElementsByClass("miniblock");

        List<String> genres = element.select("a").eachText().stream().collect(Collectors.toList());

        List<String> links = element.select("a").eachAttr("href")
                .stream().collect(Collectors.toList());

        List<Genre> genresWithLink = IntStream.range(0, genres.size())
                .mapToObj(i -> new Genre(genres.get(i), links.get(i)))
                .collect(Collectors.toList());

        HtmlDataParser.getGenres().forEach(System.out::println);
    }
}
