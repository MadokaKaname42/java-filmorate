package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public class UserResponse {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String email;

    private String login;

    private String name;

    private LocalDate birthday;

    private final Set<Long> friends = new HashSet<>();
}