package ru.practicum.shareit.booking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UnsupportedStatusError {
    public String error;
}
