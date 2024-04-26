package ru.practicum.shareit.user.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.impl.UserRepository;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    private Long counter = 1L;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(Long userId) {
        var user = users.get(userId);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с таким id не найден.");
        return user;
    }

    @Override
    public User saveUser(User user) {
        if (user.getId() == null) {
            var userId = counter++;
            user.setId(userId);
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUserById(Long userId) {
        users.remove(userId);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

}
