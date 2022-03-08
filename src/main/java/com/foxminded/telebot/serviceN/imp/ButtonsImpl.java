//package com.foxminded.telebot.serviceN.imp;
//
//import com.foxminded.telebot.dao.GenreDao;
//import com.foxminded.telebot.dao.impl.GenreDaoImpl;
//import com.foxminded.telebot.model.genre.Film;
//import com.foxminded.telebot.serviceN.Buttons;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class ButtonsImpl implements Buttons {
//    private final GenreDao genreDao = GenreDaoImpl.getInstance();
//
//
//    @Override
//    public SendMessage sendGenres(String chatId) {
//        return SendMessage.builder().chatId(chatId)
//                .text("SELECT GENRE: ")
//                .replyMarkup(InlineKeyboardMarkup.builder()
//                .keyboard(genresInButtons()).build()).build();
//    }
//
//    @Override
//    public List<List<InlineKeyboardButton>> genresInButtons() {
//        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
//        for (Film film : genreDao.index()) {
//            buttons.add(
//                    Arrays.asList(
//                            InlineKeyboardButton.builder()
//                                    .text(film.getGenre())
//                                    .callbackData("ORIGINAL:" + film.getGenre())
//                                    .build()));
//        }
//        return buttons;
//    }
//
//    public static void main(String[] args) {
//        ButtonsImpl buttons = new ButtonsImpl();
//        buttons.genresInButtons().forEach(System.out::println);
//    }
//}
