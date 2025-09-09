package ru.yandex.practicum.filmorate.storage.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Component
public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        String name = rs.getString("name");
        return Arrays.stream(Genre.values())
                .filter(genre -> genre.getDisplayName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Жанр '" + name + "' не найден"));
    }
}