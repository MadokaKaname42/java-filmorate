package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

public class UserMapper {
    public static User mapToUser(NewUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setName(request.getName());
        user.setBirthday(request.getBirthday());
        setDefaultNameIfBlank(user);
        return user;
    }

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setLogin(user.getLogin());
        userDto.setName(user.getName());
        userDto.setBirthday(user.getBirthday());
        userDto.setFriends(user.getFriends().keySet());
        return userDto;
    }

    public static User updateUserFields(User user, UpdateUserRequest userRequest) {
        if (userRequest.hasEmail()) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.hasLogin()) {
            user.setLogin(userRequest.getLogin());
        }
        if (userRequest.hasBirthday()) {
            user.setBirthday(userRequest.getBirthday());
        }
        if (userRequest.hasName()) {
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
