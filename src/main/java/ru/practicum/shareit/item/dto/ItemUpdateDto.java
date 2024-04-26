package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.User;

@Data
public class ItemUpdateDto {
    private String name;
    private String description;
    private Boolean available;
    private User user;
}
