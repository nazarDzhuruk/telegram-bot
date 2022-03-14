package com.foxminded.telebot.service;

import com.foxminded.telebot.exception.HtmlDataParserException;
import com.foxminded.telebot.model.Genre;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HtmlDataParser {
    private static final String LINK = "https://kinogo.film";
    private static final String ELEMENTS_CLASS = "miniblock";
    private static final String HTML_ELEMENT = "a";
    private static final String HTML_ATTRIBUTE = "href";
    private static Document document;
    private static Document mapper;

    private HtmlDataParser() {
    }

    static {
        try {
            document = Jsoup.connect(LINK).get();
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
        String path = LINK + genreLink + pageNumber;
        try {
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            throw new HtmlDataParserException(e.getMessage());
        }
        return mapper.getElementsByClass("zagolovki").eachText();
    }


    public static List<String> getFilmsLink(String genreLink, String pageNumber) {
        String path = LINK + genreLink + pageNumber;
        try {
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            throw new HtmlDataParserException(e.getMessage());
        }
        return mapper.getElementsByClass("zagolovki").select("a")
                .eachAttr("href").stream()
                .map(s -> s.split(LINK)[1])
                .map(s -> s.replace(".html", "")).collect(Collectors.toList());
    }

    public static String getFilmDesc(String path) {
        try {
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            throw new HtmlDataParserException(e.getMessage());
        }
        return mapper.getElementsByClass("fullimg").text();
    }

    private static List<String> getGenresAsText() {

        Elements element = document.getElementsByClass(ELEMENTS_CLASS);

        return new ArrayList<>(element.select(HTML_ELEMENT).eachText());
    }

    private static List<String> getGenresLinksFromPage() {

        Elements element = document.getElementsByClass(ELEMENTS_CLASS);

        return new ArrayList<>(element.select(HTML_ELEMENT).eachAttr(HTML_ATTRIBUTE));
    }
}
