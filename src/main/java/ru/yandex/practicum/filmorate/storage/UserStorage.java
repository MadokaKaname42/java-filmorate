package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {
    public void save(User user);

    public User findById(long id);

    public List<User> findAll();

    public void deleteById(long id);

    public Set<Long> getKeys();
}
