package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.RatingMPA;
import ru.yandex.practicum.filmorate.storage.DbRepository.GenreDbRepository;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;
    GenreDbRepository genreStorage;

    public List<FilmDto> findAllFilms() {
        log.info("");
        return filmStorage.findAll()
                .stream()
                .map(FilmMapper::toFilmDto)
                .collect(Collectors.toList());
    }

    public FilmDto findFilmById(long filmId) {
        log.info("");
        FilmDto filmDto = FilmMapper.toFilmDto(filmStorage.findByIdWithGenres(filmId).orElseThrow(() -> new NotFoundException("Film not found id: " + filmId)));
        filmDto.setGenres(genreStorage.findGenresByFilmID(filmId)
                .stream()
                .map(GenreMapper::toDto)
                .sorted(Comparator.comparing(GenreDto::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        return filmDto;
    }

    public FilmDto createFilm(FilmDto filmRequest) {
        log.info("");
        Film film = FilmMapper.toFilm(filmRequest);
        Film filmFromDb = filmStorage.save(film);
        for (GenreDto genreDto : filmRequest.getGenres()) {
            filmStorage.addGenreFilm(filmFromDb.getId(), genreDto.getId());
            filmFromDb.getGenres().add(GenreMapper.fromDto(genreDto));
        }
        filmFromDb = filmStorage.findByIdWithGenres(filmFromDb.getId()).orElseThrow();

        return FilmMapper.toFilmDto(filmFromDb);
    }

    public FilmDto updateFilm(FilmDto filmRequest) {
        log.info("");
        filmStorage.findById(filmRequest.getId()).orElseThrow(() -> new NotFoundException("Film not found id: " + filmRequest.getId()));
        Film film = FilmMapper.toFilm(filmRequest);
        Film filmFromDb = filmStorage.update(film);
        for (GenreDto genreDto : filmRequest.getGenres()) {
            filmStorage.addGenreFilm(filmFromDb.getId(), genreDto.getId());
            filmFromDb.getGenres().add(GenreMapper.fromDto(genreDto));
        }
        return FilmMapper.toFilmDto(filmFromDb);
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.findById(filmId).orElseThrow(() -> new NotFoundException("Film not found id: " + filmId));
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException("Film not found id: " + userId));
        filmStorage.addLike(filmId, userId);
    }

    public void removeLikeFilm(Long filmId, Long userId) {
        Film film = filmStorage.findById(filmId).orElseThrow(() -> new NotFoundException("Film not found id: " + filmId));
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException("Film not found id: " + userId));
        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> findPopularFilm(int count) {
        return filmStorage.getPopularFilms();
    }

    public RatingMpaDto getMpaByID(long mpaId) {
        return Arrays.stream(RatingMPA.values())
                .filter(mpa -> mpa.getId() == mpaId)
                .map(MpaMapper::toDto)
                .findFirst().orElseThrow(() -> new NotFoundException("Рейтинг не существует"));
    }

    public List<RatingMpaDto> getGetMpaAll() {
        return Arrays.stream(RatingMPA.values())
                .map(MpaMapper::toDto)
                .collect(Collectors.toList());
    }
}
