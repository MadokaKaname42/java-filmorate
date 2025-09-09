package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbRepository.UserDbRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbRepository.class})
@SpringBootTest
class FilmorateApplicationTests {
    private final UserDbRepository userStorage;

    @Test
    public void testFindUserById() {

        Optional<User> userOptional = userStorage.findById(1);

        AssertionsForClassTypes.assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        AssertionsForClassTypes.assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }
}