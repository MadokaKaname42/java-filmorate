package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.groups.OnCreate;
import ru.yandex.practicum.filmorate.validation.groups.OnUpdate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class User {
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotBlank(groups = OnCreate.class)
    @Email(groups = {OnUpdate.class, OnCreate.class})
    private String email;

    @NotNull(groups = OnCreate.class)
    @Pattern(regexp = "\\S+", groups = {OnUpdate.class, OnCreate.class})
    private String login;

    private String name;

    @Past(groups = {OnUpdate.class, OnCreate.class})
    private LocalDate birthday;

    private final Set<Long> friends = new HashSet<>();
}