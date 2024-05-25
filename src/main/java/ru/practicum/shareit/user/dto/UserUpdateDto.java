package ru.practicum.shareit.user.dto;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String name;
    @Email
    private String email;
}
