package ru.yandex.practicum.filmorate.mapper;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.util.Arrays;

@Slf4j
public class FilmMapper {
    public static Film toFilm(FilmDto request) {
        if (request == null) return null;

        Film film = new Film();
        film.setId(request.getId());
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());

        film.setRating(
                Arrays.stream(RatingMPA.values())
                        .filter(r -> r.getId() == request.getMpa().getId())
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException("Unknown id: " + request.getMpa().getId()))
            );

        if (request.getGenres() != null && !request.getGenres().isEmpty()) {
            request.getGenres()
                    .stream()
                    .map(GenreMapper::fromDto)
                            .forEach(g -> film.getGenres().add(g));
        } else {
            film.getGenres().clear();
        }
        return film;
    }

    public static FilmDto toFilmDto(Film film) {
        FilmDto dto = new FilmDto();
        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setDuration(film.getDuration());

        if(film.getRating() != null) {
            dto.setMpa(new RatingMpaDto(film.getRating().getId(), film.getRating().getDisplayName()));
        }

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            for (Genre genres : film.getGenres()) {
                GenreDto genreDto = GenreMapper.toDto(genres);
                dto.getGenres().add(genreDto);
            }
        }

        return dto;
    }
}