package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validation.groups.OnCreate;
import ru.yandex.practicum.filmorate.validation.groups.OnUpdate;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<FilmDto> findAllFilms() {
        return filmService.findAllFilms();
    }

    @GetMapping("/{id}")
    public FilmDto findByFilmById(@PathVariable Long id) {
        return filmService.findFilmById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDto createFilm(@Validated(OnCreate.class) @RequestBody FilmDto filmRequest) {
        return filmService.createFilm(filmRequest);
    }

    @PutMapping
    public FilmDto updateFilm(@Validated(OnUpdate.class) @RequestBody FilmDto filmRequest) {
        return filmService.updateFilm(filmRequest);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void removeLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.removeLikeFilm(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> findPopularFilm(@RequestParam(defaultValue = "10") int count) {
        return filmService.findPopularFilm(count);
    }
}