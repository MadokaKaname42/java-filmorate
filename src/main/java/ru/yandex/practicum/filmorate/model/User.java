package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.validation.NotAfterToday;
import ru.yandex.practicum.filmorate.validation.groups.OnCreate;
import ru.yandex.practicum.filmorate.validation.groups.OnUpdate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Component
public class User {
    private final Set<Long> friends = new HashSet<>();

    @NotNull(message = "id обязателен при обновлении", groups = OnUpdate.class)
    private Long id;

    @NotBlank(message = "email обязателен при создании", groups = OnCreate.class)

    @Email(groups = {OnUpdate.class, OnCreate.class})
    private String email;

    @NotNull(message = "логин обязателен при создании", groups = OnCreate.class)
    @Pattern(regexp = "\\S+", message = "строка не должна содержать пробелов", groups = {OnUpdate.class, OnCreate.class})
    private String login;

    private String name;

    @NotAfterToday(groups = {OnUpdate.class, OnCreate.class})
    private LocalDate birthday;
}