package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

public interface FilmStorage {
    public void save(Film film);

    public Film findById(long id);

    public List<Film> findAll();

    public void deleteById(long id);

    public Set<Long> getKeys();
}
