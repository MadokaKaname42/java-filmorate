package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.validation.NotBefore;
import ru.yandex.practicum.filmorate.validation.groups.OnCreate;
import ru.yandex.practicum.filmorate.validation.groups.OnUpdate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class NewFilmRequest {
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Size(groups = {OnCreate.class, OnUpdate.class}, max = 200)
    private String description;

    @NotBefore(minData = "1895-12-28", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private LocalDate releaseDate;

    @Positive(groups = {OnCreate.class, OnUpdate.class})
    private Integer duration;

    private MpaDto mpa;

    private final Set<GenreDto> genres = new HashSet<>();
}