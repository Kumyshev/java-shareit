package ru.practicum.shareit.user.impl;

import java.util.Collection;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

public interface UserService {

    Collection<UserDto> findAll();

    UserDto findById(Long userId);

    UserDto saveUser(UserDto userDto);

    UserDto updateUser(UserUpdateDto userUpdateDto, Long userId);

    void deleteUserById(Long userId);

    void deleteAll();
}
