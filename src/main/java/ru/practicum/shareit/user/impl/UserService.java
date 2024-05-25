package ru.practicum.shareit.user.impl;

import java.util.List;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {

    public UserDto postUser(UserDto userDto);

    public UserDto patchUser(UserDto userDto, Long userId);

    public UserDto getUser(Long userId);

    public List<UserDto> getUsers();

    public void deleteUser(Long userId);

    public User findUser(Long userId);
}
