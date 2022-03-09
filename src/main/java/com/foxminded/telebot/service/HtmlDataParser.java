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

    private HtmlDataParser() {
    }

    static {
        try {
            document = Jsoup.connect(LINK).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Genre> getGenres() {
        return IntStream.range(0, getGenresAsText().size())
                .mapToObj(i -> new Genre(getGenresAsText().get(i), getGenresLinksFromPage().get(i)))
                .collect(Collectors.toList());

    }

    public static List<String> getFilmsRating(String genreLink, String pageNumber) {
        Document pageMapper;
        String path = LINK + genreLink + pageNumber;
        try {
            pageMapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            throw new HtmlDataParserException(e.getMessage());
        }
        return pageMapper.getElementsByClass("rating_digits_1").eachText();
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
