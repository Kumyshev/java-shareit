package ru.practicum.shareit.user.dto;

import javax.validation.constraints.Email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    @Email
    private String email;
}
