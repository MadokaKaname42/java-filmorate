package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum RatingMpa {
    G(1, "G"),
    PG(2, "PG"),
    PG_13(3, "PG-13"),
    R(4, "R"),
    NC_17(5, "NC-17");

    int id;
    String displayName;

    RatingMpa(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }
}
