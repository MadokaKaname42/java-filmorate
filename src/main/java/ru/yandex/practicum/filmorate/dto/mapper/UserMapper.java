package ru.yandex.practicum.filmorate.dto.mapper;

import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserResponse;
import ru.yandex.practicum.filmorate.model.User;

public class UserMapper {
    public static User updateUserFields(NewUserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setLogin(userRequest.getLogin());
        user.setName(userRequest.getName());
        user.setBirthday(userRequest.getBirthday());
        setDefaultNameIfBlank(user);
        return user;
    }

    public static UserResponse toUserResponse(User user) {
        UserResponse userDto = new UserResponse();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setLogin(user.getLogin());
        userDto.setName(user.getName());
        userDto.setBirthday(user.getBirthday());
        userDto.setFriends(user.getFriends().keySet());
        return userDto;
    }

    public static User updateUserFields(User user, UpdateUserRequest userRequest) {
        if (userRequest.getLogin() != null && !userRequest.getLogin().isBlank()) {
            user.setLogin(userRequest.getLogin());
        }

        if (userRequest.getEmail() != null && !userRequest.getEmail().isBlank()) {
            user.setEmail(userRequest.getEmail());
        }

        if (userRequest.getBirthday() != null) {
            user.setBirthday(userRequest.getBirthday());
        }

        if (userRequest.getName() != null) {
            user.setName(userRequest.getName());
        } else {
            setDefaultNameIfBlank(user);
        }

        return user;
    }

    private static void setDefaultNameIfBlank(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
