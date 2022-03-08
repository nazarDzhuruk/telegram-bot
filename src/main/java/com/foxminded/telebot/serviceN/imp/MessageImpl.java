//package com.foxminded.telebot.serviceN.imp;
//
//import com.foxminded.telebot.dao.GenreDao;
//import com.foxminded.telebot.dao.UserDao;
//import com.foxminded.telebot.dao.impl.GenreDaoImpl;
//import com.foxminded.telebot.dao.impl.UserDaoImpl;
//import com.foxminded.telebot.model.genre.Film;
//import com.foxminded.telebot.model.genre.FilmBuilderImpl;
//import com.foxminded.telebot.model.user.BuilderImpl;
//import com.foxminded.telebot.model.User;
//import com.foxminded.telebot.serviceN.Message;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//
//import java.io.IOException;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//public class MessageImpl implements Message {
//    private final GenreDao genreDao = GenreDaoImpl.getInstance();
//    private final UserDao userDao = UserDaoImpl.getInstance();
//    private static final String URL = "https://kinogo.film";
//    private static final String CLASS_NAME = "shortimg";
//
//    @Override
//    public List<SendMessage> startMessage(String chatId) {
//        return genresToLink().keySet().stream().map(k -> SendMessage.builder()
//                .chatId(chatId)
//                .text(k).build()).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<SendMessage> filmDescription(String message, String chatId) {
//        return completeDescription(message).stream().map(film -> SendMessage.builder()
//                .chatId(chatId)
//                .text(film.getDescription() + "\n\n" + film.getImageLink())
//                .build()).collect(Collectors.toList());
//    }
//
//    @Override
//    public SendMessage helloMessage(String chatId, String nickname) {
//        if (user(chatId) == null) {
//            userDao.create(new BuilderImpl().setId(Integer.parseInt(chatId))
//                    .setNickname(nickname).build());
//        }
//        return SendMessage.builder().chatId(chatId)
//                .text("HELLO @" + nickname.toUpperCase() + " !").build();
//    }
//
//    private User user(String chatId) {
//        return userDao.index().stream().filter(user -> String.valueOf(user.getId()).equals(chatId))
//                .findAny().orElse(null);
//    }
//
//    private List<Film> completeDescription(String message) {
//        List<String> images = filmBillboard(message);
//        List<String> description = filmPortrait(message);
//
//        return IntStream.range(0, description.size()).mapToObj(i -> new FilmBuilderImpl()
//                .setDescription(description.get(i)).setImageLink(images.get(i)).build()).collect(Collectors.toList());
//    }
//
//    private List<String> filmBillboard(String selectedGenre) {
//        Document document = null;
//        try {
//            document = Jsoup.connect(URL + genresToLink()
//                    .get(selectedGenre)).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assert document != null;
//        return document.getElementsByClass("poster-br").select("img").eachAttr("data-src");
//    }
//
//    private List<String> filmPortrait(String message) {
//        Document document = null;
//        try {
//            document = Jsoup
//                    .connect(URL + genresToLink().get(message)).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assert document != null;
//        return document.getElementsByClass(CLASS_NAME).stream().map(Element::text).toList();
//    }
//
//    private Map<String, String> genresToLink() {
//        Map<String, String> genresLink = new LinkedHashMap<>();
//        genreDao.index().forEach(g -> genresLink.put(g.getGenre(), g.getLink()));
//        return genresLink;
//    }
//}