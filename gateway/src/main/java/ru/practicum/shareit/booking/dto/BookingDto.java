package ru.practicum.shareit.booking.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    public interface Create {
    }

    public interface Update {
    }

    private Long id;
    @NotNull(groups = Create.class)
    private Long itemId;
    @NotNull(groups = Create.class)
    @FutureOrPresent(groups = Create.class)
    private LocalDateTime start;
    @NotNull(groups = Create.class)
    @Future(groups = Create.class)
    private LocalDateTime end;
    private Item item;
    private Booker booker;
    private String status;
}
