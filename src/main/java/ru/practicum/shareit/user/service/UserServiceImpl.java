package ru.practicum.shareit.user.service;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.impl.UserRepository;
import ru.practicum.shareit.user.impl.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Collection<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long userId) {
        var user = repository.findById(userId);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с таким id не найден.");
        return user;
    }

    @Override
    public User saveUser(User user) {
        if (isSameUser(user) == true)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера.");
        return repository.saveUser(user);
    }

    @Override
    public User updateUser(UserDto userDto, Long userId) {
        mapper.getConfiguration().setSkipNullEnabled(true);
        var user = findById(userId);
        var userClone = User.builder().id(userId).name(user.getName()).email(user.getEmail()).build();
        mapper.map(userDto, userClone);
        if (isSameUser(userClone) == true)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера.");
        return repository.saveUser(userClone);
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
        var userList = findAll();
        for (User user : userList) {
            if (user.getEmail().equals(verifiableUser.getEmail()) && !user.getId().equals(verifiableUser.getId()))
                return true;
        }
        return false;
    }
}
