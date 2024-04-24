package ru.practicum.shareit.user.impl;

import java.util.Collection;

import ru.practicum.shareit.user.User;

public interface UserRepository {
    Collection<User> findAll();

    User findById(Long userId);

    User saveUser(User user);

    void deleteUserById(Long userId);

    void deleteAll();
}
