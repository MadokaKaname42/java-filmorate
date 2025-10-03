package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserResponse;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody NewUserRequest userRequest) {
        return userService.create(userRequest);
    }

    @GetMapping("/{userId}")
    public UserResponse findUser(@PathVariable Long userId) {
        return userService.findUser(userId);
    }

    @PutMapping
    public UserResponse update(@Valid @RequestBody UpdateUserRequest userRequest) {
        return userService.update(userRequest);
    }

    @PutMapping("/{senderId}/friends/{receiverId}")
    public List<Long> addFriend(@PathVariable Long senderId, @PathVariable Long receiverId) {
        userService.addFriend(senderId, receiverId);
        List<Long> response = new ArrayList<>();
        response.add(receiverId);
        return response;
    }

    @GetMapping("/{userId}/friends")
    public List<UserResponse> getFriends(@PathVariable Long userId) {
        return userService.getFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<UserResponse> getCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) {

        return userService.getCommonFriends(userId, otherId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.deleteFriend(userId, friendId);
    }
}