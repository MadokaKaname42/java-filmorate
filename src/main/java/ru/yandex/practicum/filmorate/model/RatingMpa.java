package ru.yandex.practicum.filmorate.model;

public enum RatingMpa {
    G(0, "G"),
    PG(1, "PG"),
    PG_13(2, "PG-13"),
    R(3, "R"),
    NC_17(4, "NC-17");

    int id;
    String displayName;

    RatingMpa(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }
}
