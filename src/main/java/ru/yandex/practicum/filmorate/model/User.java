package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.NotAfterToday;
import ru.yandex.practicum.filmorate.validation.groups.OnCreate;
import ru.yandex.practicum.filmorate.validation.groups.OnUpdate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    @NotAfterToday(groups = {OnUpdate.class, OnCreate.class})
    private LocalDate birthday;

    private Map<Long, Boolean> friends = new HashMap<>();
}