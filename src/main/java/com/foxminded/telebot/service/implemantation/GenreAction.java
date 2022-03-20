package com.foxminded.telebot.service.implemantation;

import com.foxminded.telebot.exception.TelebotServiceException;
import com.foxminded.telebot.model.Genre;
import com.foxminded.telebot.model.StaticLink;
import com.foxminded.telebot.repository.GenreRepository;
import com.foxminded.telebot.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GenreAction implements GenreService {
    private static final String EXPECTED_EXCEPTION = "Genre not found.";
    private static final String LOG = StaticLink.TEXT_PURPLE + "Genre service: " + StaticLink.TEXT_RESET;
    private static final String SUCCESS = StaticLink.TEXT_GREEN + "successfully" + StaticLink.TEXT_RESET;
    private final GenreRepository repository;

    @Autowired
    public GenreAction(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Genre addGenre(Genre genre) {
        log.trace(LOG + StaticLink.TEXT_GREEN + "add genre method accessed" + StaticLink.TEXT_RESET);
        repository.save(genre);
        log.info(LOG + "genre add " + SUCCESS);
        return genre;
    }

    @Override
    public Genre removeGenreById(int genreId) {
        log.trace(LOG + "remove genre by id method accessed");
        Genre genre = repository.findById(genreId).orElseThrow(() -> {
            log.warn(LOG + StaticLink.TEXT_RED + "remove genre by id – " + EXPECTED_EXCEPTION + StaticLink.TEXT_RESET);
            return new TelebotServiceException(EXPECTED_EXCEPTION);
        });
        repository.delete(genre);
        log.info(LOG + "genre has been deleted " + SUCCESS);
        return genre;
    }

    @Override
    public Genre modifyGenre(int genreId, Genre genre) {
        log.trace(LOG + "modify genre method accessed");
        Genre genreFromDatabase = repository.findById(genreId)
                .orElseThrow(() -> {
                    log.warn(LOG + StaticLink.TEXT_RED + "modify genre – " + EXPECTED_EXCEPTION + StaticLink.TEXT_RESET);
                    return new TelebotServiceException(EXPECTED_EXCEPTION);
                });
        genreFromDatabase.setLink(genre.getLink());
        repository.save(genreFromDatabase);
        log.info(LOG + "genre has been modified " + SUCCESS);
        return genreFromDatabase;
    }

    @Override
    public List<Genre> getAll() {
        log.trace(LOG + "get all method accessed");
        return repository.findAll();
    }
}
