package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;
import java.util.Optional;

public interface BaseStorage <T> {
    public Collection<T> findAll();

    public Optional<T> findById(long id);

    public T save(T entity);

    public void deleteById(long id);

    public T update(T entity);
}
