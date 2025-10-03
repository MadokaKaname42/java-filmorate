package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Optional;

public interface UserStorage extends BaseStorage<User> {
    Optional<User> findByEmail(String email);

    void insertFriendship(Long senderId, Long receiverId);

    public void updateFriendship(Long senderId, Long receiverId, boolean confirmed);

    public boolean containsFriendship(Long senderId, Long receiverId, Boolean filterConfirmed);
}
