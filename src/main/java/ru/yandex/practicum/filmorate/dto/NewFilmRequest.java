package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.NotBefore;

import java.time.LocalDate;
import java.util.List;

@Data
public class NewFilmRequest {
    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @NotBefore(minData = "1895-12-28")
    private LocalDate releaseDate;

    @Positive
    private Integer duration;

    private List<GenreDto> genres;

    private MpaDto mpa;
}