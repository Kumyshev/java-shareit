package ru.practicum.shareit.user.impl;

import java.util.Collection;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {

    Collection<User> findAll();

    User findById(Long userId);

    User saveUser(User user);

    User updateUser(UserDto userDto, Long userId);

    void deleteUserById(Long userId);

    void deleteAll();
}
