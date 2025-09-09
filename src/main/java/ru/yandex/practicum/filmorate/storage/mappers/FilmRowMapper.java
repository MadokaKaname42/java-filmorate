package ru.yandex.practicum.filmorate.storage.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Slf4j
@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getLong("film_id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        film.setReleaseDate(resultSet.getDate("release_date").toLocalDate());
        film.setDuration(resultSet.getInt("duration"));
        String ratingStr = resultSet.getString("rating");
        log.info(ratingStr);
        RatingMPA rating = Arrays.stream(RatingMPA.values()).filter(ratingMPA -> ratingStr.equals(ratingMPA.getDisplayName())).findFirst().orElseThrow();
        log.info(rating.getDisplayName());
        film.setRating(rating);
        log.info("Возвращаем фильм");

        return film;
    }
}