package com.foxminded.telebot.service;

import com.foxminded.telebot.model.Genre;

import java.util.List;

public interface GenreService {
    Genre addGenre(Genre genre);
    Genre removeGenreById(int genreId);
    Genre modifyGenre(int genreId, Genre genre);
    List<Genre> getAll();
}
