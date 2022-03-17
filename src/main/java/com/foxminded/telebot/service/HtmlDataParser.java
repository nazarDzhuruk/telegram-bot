package com.foxminded.telebot.service;

import com.foxminded.telebot.exception.HtmlDataParserException;
import com.foxminded.telebot.model.Genre;
import com.foxminded.telebot.model.StaticLink;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HtmlDataParser {

    private static Document document;
    private static Document mapper;

    private HtmlDataParser() {
    }

    static {
        try {
            document = Jsoup.connect(StaticLink.MAIN_LINK).get();
        } catch (IOException e) {
            throw new HtmlDataParserException(e.getMessage());
        }
    }

    public static List<Genre> getGenres() {
        return IntStream.range(0, getGenresAsText().size())
                .mapToObj(i -> new Genre(getGenresAsText().get(i), getGenresLinksFromPage().get(i)))
                .collect(Collectors.toList());

    }

    public static List<String> getTitles(String genreLink, String pageNumber) {
        String path = StaticLink.MAIN_LINK + genreLink + pageNumber;
        try {
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            throw new HtmlDataParserException(e.getMessage());
        }
        return mapper.getElementsByClass(StaticLink.ELEMENTS_CLASS_ZAGOLOVKI).eachText();
    }


    public static List<String> getFilmsLink(String genreLink, String pageNumber) {
        String path = StaticLink.MAIN_LINK + genreLink + pageNumber;
        try {
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            throw new HtmlDataParserException(e.getMessage());
        }
        return mapper.getElementsByClass(StaticLink.ELEMENTS_CLASS_ZAGOLOVKI).select(StaticLink.HTML_ELEMENT_A)
                .eachAttr(StaticLink.HREF).stream()
                .map(s -> s.split(StaticLink.MAIN_LINK)[1])
                .map(s -> s.replace(StaticLink.DOT_HTML, StaticLink.EMPTY_STRING)).collect(Collectors.toList());
    }

    public static List<String> getFilmRating(String filmLink) {
        String path = StaticLink.MAIN_LINK + filmLink;
        try {
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            throw new HtmlDataParserException(e.getMessage());
        }
        return mapper.getElementsByClass(StaticLink.RATING_DIGITS).eachText();
    }

    public static String getFilmDesc(String path) {
        try {
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            throw new HtmlDataParserException(e.getMessage());
        }
        return mapper.getElementsByClass(StaticLink.FULLIMG).text().replace(".", ".\n")
                .split("Год")[0];
    }

    private static List<String> getGenresAsText() {

        Elements element = document.getElementsByClass(StaticLink.ELEMENTS_CLASS_MINIBLOCK);

        return new ArrayList<>(element.select(StaticLink.HTML_ELEMENT_A).eachText());
    }

    private static List<String> getGenresLinksFromPage() {

        Elements element = document.getElementsByClass(StaticLink.ELEMENTS_CLASS_MINIBLOCK);

        return new ArrayList<>(element.select(StaticLink.HTML_ELEMENT_A).eachAttr(StaticLink.HREF));
    }
}