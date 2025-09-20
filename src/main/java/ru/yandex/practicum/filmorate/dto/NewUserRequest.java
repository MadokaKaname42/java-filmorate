package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.NotAfterToday;

import java.time.LocalDate;

@Data
public class NewUserRequest {
    @Email
    private String email;

    @Pattern(regexp = "\\S+")
    private String login;

    private String name;

    @NotAfterToday
    private LocalDate birthday;
}