package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserResponse;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validation.groups.OnCreate;
import ru.yandex.practicum.filmorate.validation.groups.OnUpdate;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")

    public UserResponse findUserById(@PathVariable("userId") long userId) {
        log.info("");
        return userService.findUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Validated(OnCreate.class) NewUserRequest userRequest) {
        log.info("");
        return userService.createUser(userRequest);
    }

    @PutMapping
    public UserResponse updateUser(@RequestBody @Validated(OnUpdate.class) UpdateUserRequest userRequest) {
        log.info("");
        return userService.updateUser(userRequest);
    }

    @GetMapping("/{userId}/friends")
    public List<UserResponse> getFriends(@PathVariable long userId) {
        log.info("слой контроллера, запрос друзей пользователя");
        return userService.getFriends(userId);
    }

    @PutMapping("/{senderId}/friends/{recipientId}")
    public void sendFriendshipRequest(@PathVariable long senderId, @PathVariable long recipientId) {
        log.info("Cлой контроллера");
        userService.sendFriendshipRequest(senderId, recipientId);
    }

    @DeleteMapping("/{senderId}/friends/{recipientId}")
    public void removeFriendship(@PathVariable long senderId, @PathVariable long recipientId) {
        log.info("");
        userService.removeFriendship(senderId, recipientId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public Collection<UserResponse> getCommonFriends(@PathVariable long userId, @PathVariable long otherId) {
        return userService.getCommonFriends(userId, otherId);
    }
}