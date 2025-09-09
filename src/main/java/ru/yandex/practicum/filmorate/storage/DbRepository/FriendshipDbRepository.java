package ru.yandex.practicum.filmorate.storage.DbRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


@Slf4j
@Repository
public class FriendshipDbRepository extends BaseRepository {
    private static final String INSERT_FRIENDSHIP_QUERY = "INSERT INTO friendship (sender_id, recipient_id, status) VALUES(?,?, 'PENDING')";
    private static final String GET_FRIENDS_QUERY =
            "SELECT CASE " +
                    "  WHEN sender_id = ? THEN recipient_id " +
                    "  ELSE sender_id " +
                    "END AS friend_id " +
                    "FROM friendship " +
                    "WHERE (status = 'CONFIRM' AND (sender_id = ? OR recipient_id = ?)) " +
                    "   OR (status = 'PENDING' AND sender_id = ?)";
    private static final String DELETE_FRIENDSHIP_QUERY = "DELETE FROM friendship WHERE (sender_id = ? AND recipient_id = ?) OR (sender_id = ? AND recipient_id = ?)";

    public FriendshipDbRepository(JdbcTemplate jdbc, RowMapper<Friendship> mapper) {
        super(jdbc, mapper);
    }

    public void insertFriendship(long senderId, long recipientId) {
        jdbc.update(INSERT_FRIENDSHIP_QUERY, senderId, recipientId);
    }

    public List<Long> getFriends(long userId) {
        return jdbc.query(
                GET_FRIENDS_QUERY,
                (rs, rowNum) -> rs.getLong("friend_id"),
                userId, userId, userId, userId
        );
    }

    public void removeFriendship(long sender_id, long recepient_id) {
        jdbc.update(DELETE_FRIENDSHIP_QUERY, sender_id, recepient_id, recepient_id, sender_id);
    }
}