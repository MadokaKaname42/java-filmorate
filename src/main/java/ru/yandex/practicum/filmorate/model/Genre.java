package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Genre {
    COMEDY(1, "Комедия"),
    DRAMA(2, "Драма"),
    СARTOON(3, "Мультфильм"),
    THRILLER(4, "Триллер"),
    DOCUMENTARY(5, "Документальный"),
    AСTION(6, "Боевик");

    private final int id;
    private final String displayName;
}
