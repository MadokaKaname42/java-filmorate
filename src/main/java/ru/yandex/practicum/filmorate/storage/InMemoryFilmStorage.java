package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public void save(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public Film findById(final long id) {
        if(!films.containsKey(id)) {
            log.error("Попытка получить несуществующий фильм с id {}", id);
            throw new NotFoundException(String.format("Фильм с id %s не найден!", id));
        }
        return films.get(id);
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void deleteById(long id) {
        films.remove(id);
    }

    @Override
    public Set<Long> getKeys() {
        return films.keySet();
    }
}
