package ru.practicum.shareit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
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

    @ParameterizedTest
    @CsvSource({ "aza@yandex.ru, azamat" })
    void saveUserTest(@Mock String email, @Mock String name) {
        UserDto userDto = new UserDto();
        userDto.setName(name);
        userDto.setEmail(email);
        userService.postUser(userDto);

        TypedQuery<User> query = em.createQuery("select u from users u where u.name = :name", User.class);
        User user = query.setParameter("name", name).getSingleResult();

        assertThat(user.getId(), notNullValue());
    }

}
