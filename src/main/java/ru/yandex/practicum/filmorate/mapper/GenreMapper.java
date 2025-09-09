package ru.yandex.practicum.filmorate.mapper;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Arrays;

@Component
public final class GenreMapper {
    public static Genre fromDto(GenreDto genreDto) {
        return Arrays.stream(Genre.values())
                .filter(genre -> genre.getId() == genreDto.getId())
                .findFirst().orElseThrow(() -> new NotFoundException("Жанр не найден"));
    }

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getDisplayName());
    }
}