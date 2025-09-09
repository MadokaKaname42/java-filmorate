package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.RatingMpaDto;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class RatingMpaController {
    private final FilmService filmService;

    public RatingMpaController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public RatingMpaDto getMpaById(@PathVariable Long id) {
        log.info("");
        return filmService.getMpaByID(id);
    }

    @GetMapping()
    public List<RatingMpaDto> getMpa() {
        log.info("");
        return filmService.getGetMpaAll();
    }
}