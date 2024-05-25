package ru.practicum.shareit.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
}
