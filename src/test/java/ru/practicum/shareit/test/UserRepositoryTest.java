package ru.practicum.shareit.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Transactional
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .name("Azamat")
                .email("aza@yandex.ru")
                .build();
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.delete(user);
    }

    @Test
    void givenUser_whenSaved_thenCanBeFoundById() {
        User savedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals(user.getName(), savedUser.getName());
    }
}
