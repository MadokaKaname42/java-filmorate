package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validation.NotBefore;
import ru.yandex.practicum.filmorate.validation.groups.OnCreate;
import ru.yandex.practicum.filmorate.validation.groups.OnUpdate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotBlank(groups = OnCreate.class)
    private String name;

    @Size(groups = {OnCreate.class, OnUpdate.class}, max = 200)
    private String description;

    @NotBefore(minData = "1895-12-28", groups = {OnCreate.class, OnCreate.class})
    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private LocalDate releaseDate;

    @Positive(message = "Длительность не может быть отрицательной", groups = {OnCreate.class, OnUpdate.class})
    private Integer duration;

    private Set<Long> likes = new HashSet<>();

    private Set<Genre> genres = new HashSet<>();

    private RatingMPA rating;
}