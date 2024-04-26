package ru.practicum.shareit.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.impl.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService service;

    @GetMapping
    public Collection<UserDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable("userId") Long userId) {
        return service.findById(userId);
    }

    @PostMapping
    public UserDto saveUser(@Valid @RequestBody UserDto userDto) {
        return service.saveUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto, @PathVariable("userId") Long userId) {
        if (userUpdateDto.getName() != null && userUpdateDto.getName().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Некорректный запрос.");
        return service.updateUser(userUpdateDto, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable("userId") Long userId) {
        service.deleteUserById(userId);
    }
}
