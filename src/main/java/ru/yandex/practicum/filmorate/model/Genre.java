package ru.yandex.practicum.filmorate.model;

public enum Genre {
    COMEDY(1, "Комедия"),
    DRAMA(2, "Драма"),
    CARTOON(3, "Мультфильм"),
    THRILLER(4, "Триллер"),
    DOCUMENTARY(5, "Документальный"),
    ACTION(6, "Боевик");

    private int id;
    private String displayName;

    Genre(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }
}
