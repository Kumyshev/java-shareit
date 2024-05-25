package ru.practicum.shareit.user.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.impl.UserRepository;
import ru.practicum.shareit.user.impl.UserService;
import ru.practicum.shareit.user.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;

    @Override
    public Collection<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long userId) {
        var user = repository.findById(userId);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с таким id не найден.");
        return userMapper.toDto(user);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        var user = userMapper.toUser(userDto);
        if (isSameUser(user) == true)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера.");
        return userMapper.toDto(repository.saveUser(user));
    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto, Long userId) {
        var user = findById(userId);
        var userClone = User.builder().id(userId).name(user.getName()).email(user.getEmail()).build();
        userMapper.toUpdateUser(userClone, userUpdateDto);
        if (isSameUser(userClone) == true)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера.");
        return userMapper.toDto(repository.saveUser(userClone));
    }

    @Override
    public void deleteUserById(Long userId) {
        repository.deleteUserById(userId);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    boolean isSameUser(User verifiableUser) {
        var userList = findAll()
                .stream()
                .map(userMapper::toUser)
                .collect(Collectors.toList());

        for (User user : userList) {
            if (user.getEmail().equals(verifiableUser.getEmail()) && !user.getId().equals(verifiableUser.getId()))
                return true;
        }
        return false;
    }
}
