package ru.yandex.practicum.filmorate.storage.DbRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.RatingMPA;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.mappers.FilmRowMapper;

import java.util.*;

@Slf4j
@Primary
@Repository
public class FilmDbRepository extends BaseRepository<Film> implements FilmStorage {
    private static final String INSERT_QUERY = "INSERT INTO films (name, description, release_date, duration, rating)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM films WHERE film_id = ?";
    private static final String FIND_USER_WHO_LIKES_FILM = "SELECT user_id FROM film_like WHERE film_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM films ORDER BY film_id";
    private static final String DELETE_QUERY = "DELETE FROM films WHERE film_id = ?";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?," +
            " duration = ?, rating = ? WHERE film_id = ?";
    private static final String ADD_LIKE = "MERGE INTO film_like (film_id, user_id) KEY(film_id, user_id) " +
            "VALUES (?, ?);";
    private static final String DELETE_LIKE = "DELETE FROM film_like WHERE film_id = ? AND user_id = ?";
    protected static final String INSERT_GENRE = "INSERT INTO film_genre (film_id, genre_id) VALUES(?, ?)";
     String GET_POPULAR_FILMS_QUERY =
            "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating, " +
                    "       COUNT(fl.user_id) AS like_count " +
                    "FROM films f " +
                    "LEFT JOIN film_like fl ON f.film_id = fl.film_id " +
                    "GROUP BY f.film_id, f.name, f.description, f.release_date, f.duration, f.rating " +
                    "ORDER BY like_count DESC";
    private static final String FIND_FILMS_WITH_GENRES_QUERY = """
        SELECT f.film_id,
               f.name,
               f.description,
               f.release_date,
               f.duration,
               f.rating,
               g.genre_id,
               g.name AS genre_name
        FROM films f
        LEFT JOIN film_genre fg ON f.film_id = fg.film_id
        LEFT JOIN genres g ON fg.genre_id = g.genre_id
        WHERE f.film_id = ?
    """;


    public FilmDbRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public List<Film> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Film> findById(long filmId) {
        return findOne(FIND_BY_ID_QUERY, filmId);
    }

    @Override
    public Film save(Film film) {
        log.info("Слой репозитория. Попытка сохранить фильм");
        long id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRating().getDisplayName()
        );
        film.setId(id);
        return film;
    }

    @Override
    public Optional<Film> findByIdWithGenres(Long id) {
        String sql = """
        SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating,
               g.genre_id, g.name AS genre_name
        FROM films f
        LEFT JOIN film_genre fg ON f.film_id = fg.film_id
        LEFT JOIN genres g ON fg.genre_id = g.genre_id
        WHERE f.film_id = ?
    """;

        Film film = jdbc.query(sql, rs -> {
            Film f = null;
            Set<Genre> genres = new HashSet<>();
            while (rs.next()) {
                if (f == null) {
                    f = new Film();
                    f.setId(rs.getLong("film_id"));
                    f.setName(rs.getString("name"));
                    f.setDescription(rs.getString("description"));
                    f.setReleaseDate(rs.getDate("release_date").toLocalDate());
                    f.setDuration(rs.getInt("duration"));
                    String ratingStr = rs.getString("rating");
                    RatingMPA rating = Arrays.stream(RatingMPA.values()).filter(ratingMPA -> ratingStr.equals(ratingMPA.getDisplayName())).findFirst().orElseThrow();
                    f.setRating(rating);
                }

                int genreId = rs.getInt("genre_id");
                if (!rs.wasNull()) {
                    // ищем Genre по id через стрим
                    Genre genre = Arrays.stream(Genre.values())
                            .filter(g -> g.getId() == genreId)
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Unknown genre id: " + genreId));
                    genres.add(genre);
                }
            }

            if (f != null) f.setGenres(genres);
            return f;
        }, id);

        return Optional.ofNullable(film);
    }


    @Override
    public void deleteById(long filmId) {
        delete(DELETE_QUERY, filmId);
    }

    @Override
    public Film update(Film film) {
        log.info("Попытка обновить фильм: id={}, name={}", film.getId(), film.getName());
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRating().getDisplayName(),
                film.getId()
        );
        return film;
    }

    @Override
    public void addLike(long filmId, long userId) {
        update(ADD_LIKE, filmId, userId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        jdbc.update(DELETE_LIKE, filmId, userId);
    }

    @Override
    public void addGenreFilm(long filmId, long genreId) {
        update(INSERT_GENRE, filmId, genreId);
    }

    @Override
    public List<Film> getPopularFilms() {
        return findMany(GET_POPULAR_FILMS_QUERY);
    }
}