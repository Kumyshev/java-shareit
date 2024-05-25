package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingInfo {
    private Long id;
    private Long bookerId;
}
