package ru.practicum.shareit.user.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@Component
public class UserMapper {
    ModelMapper mapper = new ModelMapper();

    public UserDto toDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public User toUser(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

    public User toUpdateUser(User user, UserUpdateDto userUpdateDto) {
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(userUpdateDto, user);
        return user;
    }
}
