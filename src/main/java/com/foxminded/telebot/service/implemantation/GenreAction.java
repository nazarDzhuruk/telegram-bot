package com.foxminded.telebot.service.implemantation;

import com.foxminded.telebot.exception.TelebotServiceException;
import com.foxminded.telebot.model.Genre;
import com.foxminded.telebot.repository.GenreRepository;
import com.foxminded.telebot.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreAction implements GenreService {
    private static final String EXPECTED_EXCEPTION = "Genre not found.";
    private final GenreRepository repository;

    @Autowired
    public GenreAction(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Genre addGenre(Genre genre) {
        repository.save(genre);
        return genre;
    }

    @Override
    public Genre removeGenreById(int genreId) {
        Genre genre = repository.findById(genreId).orElseThrow(() -> new TelebotServiceException(EXPECTED_EXCEPTION));
        repository.delete(genre);
        return genre;
    }

    @Override
    public Genre modifyGenre(int genreId, Genre genre) {
        Genre genreFromDatabase = repository.findById(genreId)
                .orElseThrow(() -> new TelebotServiceException(EXPECTED_EXCEPTION));
        genreFromDatabase.setLink(genre.getLink());
        repository.save(genreFromDatabase);
        return genreFromDatabase;
    }

    @Override
    public List<Genre> getAll() {
        return repository.findAll();
    }
}
