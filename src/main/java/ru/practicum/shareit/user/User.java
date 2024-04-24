package ru.practicum.shareit.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
}
