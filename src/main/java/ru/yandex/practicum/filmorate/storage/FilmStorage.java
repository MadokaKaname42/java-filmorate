package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage extends BaseStorage<Film> {
    public void addLike(long filmId, long userId);

    public void removeLike(long filmId, long userId);

    public void addGenreFilm(long filmId, long genreId);

    public List<Film> getPopularFilms();
}
