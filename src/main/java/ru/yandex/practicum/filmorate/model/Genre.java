package ru.yandex.practicum.filmorate.model;

public enum Genre {
    COMEDY(0, "Комедия"),
    DRAMA(1, "Драма"),
    CARTOON(2, "Мультфильм"),
    THRILLER(3, "Триллер"),
    DOCUMENTARY(4, "Документальный"),
    ACTION(5, "Боевик");

    private int id;
    private String displayName;
    Genre(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }
}
