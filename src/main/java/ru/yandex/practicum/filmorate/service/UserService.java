package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserResponse;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbRepository.FriendshipDbRepository;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipDbRepository friendshipRepository;

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public UserResponse findUserById(long userId) {
        return UserMapper.mapToUserDto(userStorage.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден с ID: " + userId)));
    }

    public UserResponse createUser(NewUserRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            request = setDefaultNameIfBlank(request);
        }

        return UserMapper.mapToUserDto(userStorage.save(UserMapper.mapToUser(request)));
    }

    public UserResponse updateUser(UpdateUserRequest request) {
        User updatedUser = userStorage.findById(request.getId())
                .map(user -> UserMapper.updateUserFields(user, request))
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        updatedUser = userStorage.update(updatedUser);
        log.info("Обновлён пользователь: id={}, login={}, email={}", updatedUser.getId(), updatedUser.getLogin(), updatedUser.getEmail());
        return UserMapper.mapToUserDto(updatedUser);
    }

    private NewUserRequest setDefaultNameIfBlank(NewUserRequest user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("У пользователя установлено имя по умолчанию: {}", user.getName());
        }
        return user;
    }

    public void sendFriendshipRequest(long senderId, long recipientId) {
        validateFriendshipRequest(senderId, recipientId);
        friendshipRepository.insertFriendship(senderId, recipientId);
    }

    public List<UserResponse> getFriends(long userId) {
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return friendshipRepository.getFriends(userId)
                .stream()
                .map(id -> userStorage.findById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден")))
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public void removeFriendship(long senderId, long recipientId) {
        userStorage.findById(senderId).orElseThrow(() -> new NotFoundException(""));
        userStorage.findById(recipientId).orElseThrow(() -> new NotFoundException(""));
        friendshipRepository.removeFriendship(senderId, recipientId);
    }

    public List<UserResponse> getCommonFriends(long userId, long otherId) {
        userStorage.findById(userId).orElseThrow(() -> new NotFoundException(""));
        userStorage.findById(otherId).orElseThrow(() -> new NotFoundException(""));
        List<Long> userFriend = friendshipRepository.getFriends(userId);
        List<Long> otherUserFriends = friendshipRepository.getFriends(otherId);

        return userFriend.stream()
                .filter(otherUserFriends::contains)
                .map(id -> userStorage.findById(id).orElseThrow())
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    private void validateFriendshipRequest(long senderId, long recepierId) {
        if (senderId == recepierId) {
            throw new IllegalArgumentException("Нельзя добавить в друзья самого себя");
        }
        userStorage.findById(senderId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + senderId));
        userStorage.findById(recepierId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + recepierId));

        boolean exists = friendshipRepository.getFriends(senderId)
                .stream()
                .anyMatch(existingFriendId -> existingFriendId.equals(recepierId));

        boolean existReverse = friendshipRepository.getFriends(recepierId)
                .stream()
                .anyMatch(existingFriendId -> existingFriendId.equals(senderId));

        if (exists || existReverse) {
            throw new IllegalStateException(
                    String.format("Дружба между пользователями %d и %d уже существует", senderId, recepierId)
            );
        }
    }
}