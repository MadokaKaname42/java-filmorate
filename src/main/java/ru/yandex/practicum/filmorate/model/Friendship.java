package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friendship {
    private Long senderId;
    private Long receiverId;
    boolean confirmed;
}
