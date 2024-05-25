package ru.practicum.shareit.comment.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {

    public interface Create {
    }

    public interface Update {
    }

    private Long id;

    @NotEmpty(groups = Create.class)
    @NotBlank(groups = Create.class)
    private String text;

    private String authorName;

    private LocalDateTime created;
}
