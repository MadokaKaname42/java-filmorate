package ru.yandex.practicum.filmorate.storage;

import java.util.List;
import java.util.Optional;

public interface BaseStorage <T> {
    public List<T> findAll();

    public Optional<T> findById(long id);

    public void save(T entity);

    public void deleteById(long id);

    public Optional<T> update(T entity);
}
