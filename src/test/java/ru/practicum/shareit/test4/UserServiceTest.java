package ru.practicum.shareit.test4;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl service;

    @Mock
    UserRepository dao;

    @Mock
    UserMapper mapper;

    @Test
    void testCreateOrSaveUser() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("azamat")
                .email("aza@yandex.ru")
                .build();
        service.postUser(userDto);

        verify(dao, times(1)).save(mapper.toUser(userDto));
    }

}
