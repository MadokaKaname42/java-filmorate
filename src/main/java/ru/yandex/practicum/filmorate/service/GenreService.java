package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DbRepository.FilmDbRepository;
import ru.yandex.practicum.filmorate.storage.DbRepository.GenreDbRepository;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class GenreService {
    private final GenreDbRepository genreStorage;
    private final FilmStorage filmStorage;

    public GenreDto findGenreById(long id) {
        return GenreMapper.toDto(genreStorage.findById(id).orElseThrow(() -> new NotFoundException("Жанр не найден")));
    }

    public List<GenreDto> findAllGenres() {
        log.info("Сервис жанров");
        return genreStorage.findAll()
                .stream()
                .map(GenreMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<GenreDto> getGenresByFilm(long filmId) {
        Film film = filmStorage.findById(filmId).orElseThrow(() -> new NotFoundException("Фильм не найден"));
        return genreStorage.findGenresByFilmID(filmId)
                .stream()
                .map(GenreMapper::toDto)
                .collect(Collectors.toList());
    }
}
