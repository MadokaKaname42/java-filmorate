package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.filmorate.validation.NotAfterToday;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class NewUserRequest {
    @NotBlank
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "\\S+")
    private String login;

    private String name;

    @NotAfterToday
    private LocalDate birthday;
}
