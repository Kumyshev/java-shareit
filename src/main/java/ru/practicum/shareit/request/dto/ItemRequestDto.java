package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;

@Data
@Builder
public class ItemRequestDto {

    public interface Create {
    }

    public interface Update {
    }

    private Long id;

    @NotBlank(groups = Create.class)
    private String description;

    private Long requestor_id;

    private LocalDateTime created;

    private List<ItemDto> items;
}
