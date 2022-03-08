//package com.foxminded.telebot;
//
//import com.foxminded.telebot.dao.GenreDao;
//import com.foxminded.telebot.dao.impl.GenreDaoImpl;
//import com.foxminded.telebot.model.genre.Film;
//import com.foxminded.telebot.serviceN.imp.MessageImpl;
//import com.foxminded.telebot.source.BotConfig;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//public class BotCore extends TelegramLongPollingBot {
//    private static final String START = "/start";
//    private final GenreDao genreDao = GenreDaoImpl.getInstance();
//    private final MessageImpl message = new MessageImpl();
//
//    @Override
//    public String getBotUsername() {
//        return BotConfig.BOT_ID;
//    }
//
//    @Override
//    public String getBotToken() {
//        return BotConfig.BOT_TOKEN;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            if (update.getMessage().getText().equals(START)) {
//                message.startMessage(update.getMessage().getChatId().toString())
//                        .forEach(this::executeMessage);
//                executeMessage(message
//                        .helloMessage(update.getMessage()
//                                .getChatId().toString(), update.getMessage().getChat().getUserName()));
//            }
//            if (genre(update) != null) {
//                String chatId = update.getMessage().getChatId().toString();
//                String selectedGenre = update.getMessage().getText();
//                message.filmDescription(selectedGenre, chatId).forEach(this::executeMessage);
//            }
//        }
//    }
////
////    private Film genre(Update update) {
////        return genreDao.index().stream()
////                .filter(g -> g.getGenre().equals(update.getMessage().getText())).findAny().orElse(null);
////    }
//
//    private <E extends BotApiMethod> void executeMessage(E sendMessage) {
//        try {
//            execute(sendMessage);
//            Thread.sleep(200);
//        } catch (TelegramApiException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
