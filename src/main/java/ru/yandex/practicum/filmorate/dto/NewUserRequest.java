package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.NotAfterToday;
import ru.yandex.practicum.filmorate.validation.groups.OnCreate;
import ru.yandex.practicum.filmorate.validation.groups.OnUpdate;

import java.time.LocalDate;

@Data
public class NewUserRequest {
    private String email;
    @NotNull(message = "логин обязателен при создании", groups = OnCreate.class)
    @Pattern(regexp = "\\S+", message = "строка не должна содержать пробелов", groups = {OnUpdate.class, OnCreate.class})
    private String login;
    private String name;
    @NotAfterToday(groups = {OnUpdate.class, OnCreate.class})
    private LocalDate birthday;
    private String password;
}