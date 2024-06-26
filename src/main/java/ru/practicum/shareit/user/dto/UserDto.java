package ru.practicum.shareit.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    public interface Create {
    }

    public interface Update {
    }

    private Long id;

    @NotBlank(groups = Create.class)
    private String name;
    @NotNull(groups = Create.class)
    @Email(groups = { Create.class, Update.class })
    private String email;
}
