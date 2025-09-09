package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.NotBefore;
import ru.yandex.practicum.filmorate.validation.groups.OnCreate;
import ru.yandex.practicum.filmorate.validation.groups.OnUpdate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class FilmDto {
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Size(groups = {OnCreate.class, OnUpdate.class}, max = 200)
    private String description;

    @NotBefore(minData = "1895-12-28", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private LocalDate releaseDate;

    @Positive(message = "Длительность не может быть отрицательной", groups = {OnCreate.class, OnUpdate.class})
    private Integer duration;

    private RatingMpaDto mpa;

    private Set<GenreDto> genres = new HashSet<>();
}