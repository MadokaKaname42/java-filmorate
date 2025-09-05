package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User findUser(Long userId) {
        return userStorage.findById(userId);
    }

    public void create(User user) {
        setDefaultNameIfBlank(user);
        user.setId(getNextId());
        userStorage.save(user);

        log.info("Создан пользователь: id={}, login={}, email={}", user.getId(), user.getLogin(), user.getEmail());
    }

    public User update(User newUser) {
        User oldUser = userStorage.findById(newUser.getId());

        if (newUser.getLogin() != null && !newUser.getLogin().isBlank()) {
            oldUser.setLogin(newUser.getLogin());
        }

        if (newUser.getEmail() != null && !newUser.getEmail().isBlank()) {
            oldUser.setEmail(newUser.getEmail());
        }

        if (newUser.getBirthday() != null) {
            oldUser.setBirthday(newUser.getBirthday());
        }

        if (newUser.getName() != null) {
            oldUser.setName(newUser.getName());
        }

        setDefaultNameIfBlank(oldUser);

        log.info("Обновлён пользователь: id={}, login={}, email={}", oldUser.getId(), oldUser.getLogin(), oldUser.getEmail());
        return oldUser;
    }

    public void addFriend(Long userId, Long friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);

        if (user.getFriends().contains(friendId)) {
            log.warn("Пользователь id={} уже добавил в друзья пользователя id={}", userId, friendId);
        } else {
            user.getFriends().add(friendId);
            friend.getFriends().add(userId);
            log.info("Пользователь id={} добавил в друзья пользователя id={}", userId, friendId);
        }
    }

    public Collection<User> getFriends(Long userId) {
        User user = userStorage.findById(userId);

        return user.getFriends()
                .stream()
                .map(userStorage::findById)
                .toList();
    }

    public Collection<User> getGeneralFriends(Long userId, Long otherId) {
        User user = userStorage.findById(userId);
        User other = userStorage.findById(otherId);

        Set<Long> userFriends = user.getFriends();
        return other.getFriends()
                .stream()
                .filter(userFriends::contains)
                .map(userStorage::findById)
                .toList();
    }

    public void deleteFriend(Long userId, Long friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        log.info("Пользователь id={} удалил из друзей пользователя id={}", userId, friendId);
    }

    private long getNextId() {
        long currentMaxId = userStorage.getKeys()
                .stream()
                .mapToLong(id -> id)
                .max().orElse(0);
        return ++currentMaxId;
    }

    private void setDefaultNameIfBlank(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("У пользователя id={} установлено имя по умолчанию: {}", user.getId(), user.getName());
        }
    }
}