package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.validation.NotBefore;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class UpdateFilmRequest {
    @NotNull
    private Long id;

    private String title;

    @Size(max = 200)
    private String description;

    @NotBefore(minData = "1895-12-28")
    private LocalDate releaseDate;

    @Positive
    private Integer duration;

    private MpaDto mpa;

    private final Set<GenreDto> genres = new HashSet<>();
}