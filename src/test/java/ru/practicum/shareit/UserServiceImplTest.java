package ru.practicum.shareit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.impl.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Transactional
@SpringBootTest(properties = "db.name=test", webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImplTest {

    private final EntityManager em;
    private final UserService userService;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = UserDto.builder()
                .name("Azamat")
                .email("aza@yandex.ru")
                .build();
        userService.postUser(userDto);
    }

    @Test
    void saveUserTest() {

        TypedQuery<User> query = em.createQuery("select u from users u where u.name = :name", User.class);
        User user = query.setParameter("name", userDto.getName()).getSingleResult();

        assertThat(user.getId(), notNullValue());
    }

}
