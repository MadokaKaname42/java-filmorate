package ru.yandex.practicum.filmorate.storage.DbRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.BaseStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class GenreDbRepository extends BaseRepository implements BaseStorage<Genre> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM genres ORDER BY genre_id";
    private static final String FIND_FILM_QUERY = "SELECT fg.genre_id, g.name FROM genres g " +
            "JOIN film_genre fg ON g.genre_id = fg.genre_id WHERE fg.film_id = ? ORDER BY g.genre_id";
    private static final String FIND_GENRE_BY_ID = "SELECT * FROM genres WHERE genre_id = ?";

    public GenreDbRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<Genre> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Genre> findById(long id) {
        return findOne(FIND_GENRE_BY_ID, id);
    }

    public List<Genre> findGenresByFilmID(long filmId) {
        log.info("Получаем жанры");
        return findMany(FIND_FILM_QUERY, filmId);
    }

    @Override
    public Genre save(Genre entity) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public Genre update(Genre entity) {
        return null;
    }
}
