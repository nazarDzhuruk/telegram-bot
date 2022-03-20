package com.foxminded.telebot.service;

import com.foxminded.telebot.exception.HtmlDataParserException;
import com.foxminded.telebot.model.Genre;
import com.foxminded.telebot.model.StaticLink;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class HtmlDataParser {

    private static Document document;
    private static Document mapper;
    private static final String LOG = StaticLink.TEXT_BLUE + "HTML data parser: " + StaticLink.TEXT_RESET;
    private static final String SUCCESS = StaticLink.TEXT_GREEN + "successfully" + StaticLink.TEXT_RESET;

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
        log.trace(LOG + "titles parsing method accessed");
        String path = StaticLink.MAIN_LINK + genreLink + pageNumber;
        try {
            log.info(LOG + path + " building connection");
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            log.warn(LOG + path + " connection aborted");
            throw new HtmlDataParserException(e.getMessage());
        }
        log.info(LOG + path + " titles parsed " + SUCCESS);
        return mapper.getElementsByClass(StaticLink.ELEMENTS_CLASS_ZAGOLOVKI).eachText();
    }


    public static List<String> getFilmsLink(String genreLink, String pageNumber) {
        log.trace(LOG + "parsing films link; method accessed");
        String path = StaticLink.MAIN_LINK + genreLink + pageNumber;
        try {
            log.info(LOG + path + " building connection");
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            log.warn(LOG + path + " connection aborted");
            throw new HtmlDataParserException(e.getMessage());
        }
        log.info(LOG + path + " links parsed " + SUCCESS + " formatting...");
        return mapper.getElementsByClass(StaticLink.ELEMENTS_CLASS_ZAGOLOVKI).select(StaticLink.HTML_ELEMENT_A)
                .eachAttr(StaticLink.HREF).stream()
                .map(s -> s.split(StaticLink.MAIN_LINK)[1])
                .map(s -> s.replace(StaticLink.DOT_HTML, StaticLink.EMPTY_STRING)).collect(Collectors.toList());
    }

    public static List<String> getFilmRating(String filmLink) {
        log.trace(LOG + "parsing films rating; method accessed");
        String path = StaticLink.MAIN_LINK + filmLink;
        try {
            log.info(LOG + path + " building connection");
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            log.warn(LOG + path + " connection aborted");
            throw new HtmlDataParserException(e.getMessage());
        }
        log.info(LOG + path + " rating parsed " + SUCCESS);
        return mapper.getElementsByClass(StaticLink.RATING_DIGITS).eachText();
    }

    public static String getFilmDesc(String path) {
        log.trace(LOG + "film description method accepted");
        try {
            log.info(LOG + path + "building connection");
            mapper = Jsoup.connect(path).get();
        } catch (IOException e) {
            log.warn(LOG + path + "connection aborted");
            throw new HtmlDataParserException(e.getMessage());
        }
        log.info(LOG + "film description parsed " + SUCCESS);
        return mapper.getElementsByClass(StaticLink.FULLIMG).text().replace(".", ".\n")
                .split("Год")[0];
    }

    private static List<String> getGenresAsText() {
        log.trace(LOG + "parsing genres method accessed");
        Elements element = document.getElementsByClass(StaticLink.ELEMENTS_CLASS_MINIBLOCK);
        log.info(LOG + "genres parsed " + SUCCESS);
        return new ArrayList<>(element.select(StaticLink.HTML_ELEMENT_A).eachText());
    }

    private static List<String> getGenresLinksFromPage() {
        log.trace(LOG + "parsing genres link method accessed");
        Elements element = document.getElementsByClass(StaticLink.ELEMENTS_CLASS_MINIBLOCK);
        log.info(LOG + "genres link parsed " + SUCCESS);
        return new ArrayList<>(element.select(StaticLink.HTML_ELEMENT_A).eachAttr(StaticLink.HREF));
    }
}