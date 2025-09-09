package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.RatingMpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.util.Arrays;

public class MpaMapper {
    public static RatingMPA fromDto(RatingMpaDto mpaDto) {
        return Arrays.stream(RatingMPA.values())
                .filter(rating -> rating.getId() == mpaDto.getId())
                .findFirst().orElseThrow(() -> new NotFoundException("Жанр не найден"));
    }

    public static RatingMpaDto toDto(RatingMPA mpa) {
        return new RatingMpaDto(mpa.getId(), mpa.getDisplayName());
    }
}
