package ru.practicum.shareit.item.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.model.Booking;
import ru.practicum.shareit.user.User;

@Data
@Builder
public class ItemDto {
    public interface Create {
    }

    public interface Update {
    }

    private Long id;
    @NotBlank(groups = Create.class)
    private String name;
    @NotBlank(groups = Create.class)
    private String description;
    @NotNull(groups = Create.class)
    private Boolean available;
    private User owner;
    private Booking lastBooking;
    private Booking nextBooking;
    private List<CommentDto> comments;
    private Long requestId;
}
