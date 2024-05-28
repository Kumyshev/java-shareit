package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Booking;

public interface BookingInfo {
    public Booking findLastBooking(Long userId, Long itemId);
    public Booking findNextBooking(Long userId, Long itemId);
}
