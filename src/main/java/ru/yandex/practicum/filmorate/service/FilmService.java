package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        film.setId(getNextId());

        filmStorage.save(film);
        log.info("Создан фильм: id={}, name={}", film.getId(), film.getName());
        return film;
    }

    public Film update(Film newFilm) {
        Film oldFilm = filmStorage.findById(newFilm.getId());

        if (newFilm.getName() != null && !newFilm.getName().isBlank()) {
            oldFilm.setName(newFilm.getName());
            log.debug("Поле name фильма с id={} обновлено, новое значение name={}", oldFilm.getId(), oldFilm.getName());
        }

        if (newFilm.getDescription() != null && !newFilm.getDescription().isBlank()) {
            oldFilm.setDescription(newFilm.getDescription());
            log.debug("Поле description фильма с id={} обновлено, новое значение descriprion={}", oldFilm.getId(), oldFilm.getDescription());
        }

        if (newFilm.getReleaseDate() != null) {
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            log.debug("Поле releaseDate фильма с id={} обновлено, новое значение releaseDate={}", oldFilm.getId(), oldFilm.getReleaseDate());
        }

        if (newFilm.getDuration() != null) {
            oldFilm.setDuration(newFilm.getDuration());
            log.debug("Поле duration фильма с id={} обновлено, новое значение duration={}", oldFilm.getId(), oldFilm.getDuration());
        }

        log.info("Обновлён фильм: id={}, name={}", oldFilm.getId(), oldFilm.getName());
        return oldFilm;
    }

    public Film likeFilm(Long filmId, Long userId) {
        Film film = filmStorage.findById(filmId);
        userStorage.findById(userId);
        film.getLikes().add(userId);
        log.info("Пользователь id={} поставил лайк фильму id={}", userId, filmId);
        return film;
    }

    public Film removeLikeFilm(Long filmId, Long userId) {
        Film film = filmStorage.findById(filmId);
        userStorage.findById(userId);
        film.getLikes().remove(userId);
        log.info("Пользователь id={} удалил лайк с фильма id={}", userId, filmId);
        return film;
    }

    public Collection<Film> findPopularFilm() {
        return filmStorage.findAll()
                .stream()
                .sorted(Comparator.comparing(film -> film.getLikes().size()))
                .toList()
                .reversed();
    }

    private long getNextId() {
        long currentMaxId = filmStorage.getKeys()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
