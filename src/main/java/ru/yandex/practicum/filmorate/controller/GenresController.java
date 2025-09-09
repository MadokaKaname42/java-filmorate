package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/genres")
public class GenresController {
    private final GenreService genreService;

    @GetMapping("/{id}")
    public GenreDto getGenreId(@PathVariable Long id) {
        log.info("");
        return genreService.findGenreById(id);
    }

    @GetMapping()
    public Collection<GenreDto> findAllGenres() {
        log.info("");
        List<GenreDto> genres = genreService.findAllGenres();
        return genres;
    }
}