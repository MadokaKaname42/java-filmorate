package ru.yandex.practicum.filmorate.dto;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private String password;

    public boolean hasId() {
        return ! (email == null);
    }

    public boolean hasEmail() {
        return ! (email == null || email.isBlank());
    }

    public boolean hasLogin() {
        return ! (login == null || login.isBlank());
    }

    public boolean hasName() {
        return ! (login == null || login.isBlank());
    }

    public boolean hasBirthday() {
        return ! (birthday == null);
    }

    public boolean hasPassword() {
        return ! (password == null || password.isBlank());
    }
}