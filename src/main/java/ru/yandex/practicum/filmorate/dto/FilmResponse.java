package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
public class FilmResponse {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String title;

    private String description;

    private LocalDate releaseDate;

    private Integer duration;

    private MpaDto mpa;
    @Getter
    private final Set<GenreDto> genres = new HashSet<>();

    @Getter
    private final Set<Long> likes = new HashSet<>();
}
