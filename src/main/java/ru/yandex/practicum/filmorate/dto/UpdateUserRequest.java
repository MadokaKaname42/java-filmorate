package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.NotAfterToday;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    @NotNull
    private Long id;

    @Email
    private String email;

    @Pattern(regexp = "\\S+")
    private String login;

    private String name;

    @NotAfterToday
    private LocalDate birthday;

    public boolean hasEmail() {
        return !(email == null || email.isBlank());
    }

    public boolean hasLogin() {
        return !(login == null || login.isBlank());
    }

    public boolean hasName() {
        return !(name == null || name.isBlank());
    }

    public boolean hasBirthday() {
        return !(birthday == null);
    }
}
