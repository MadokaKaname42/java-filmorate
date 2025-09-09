package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.testng.annotations.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbRepository.UserDbRepository;

import java.util.Optional;

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