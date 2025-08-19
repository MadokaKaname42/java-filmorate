package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.NotBefore;
import ru.yandex.practicum.filmorate.validation.groups.OnCreate;
import ru.yandex.practicum.filmorate.validation.groups.OnUpdate;

import java.time.LocalDate;

@Data
public class Film {
    @NotNull(message = "id обязательно", groups = OnUpdate.class)
    private Long id;

    @NotBlank(message = "Название обязательно", groups = OnCreate.class)
    private String name;

    @Size(message = "Описание должно быть не длиннее {max} символов", groups = {OnCreate.class, OnUpdate.class}, max = 200)
    private String description;

    @NotBefore(minData = "1895-12-28", groups = {OnCreate.class, OnCreate.class})
    private LocalDate releaseDate;

    @Positive(message = "Длительность не может быть отрицательной", groups = {OnCreate.class, OnUpdate.class})
    private Long duration;
}