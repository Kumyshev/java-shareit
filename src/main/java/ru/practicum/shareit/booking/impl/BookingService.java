package ru.practicum.shareit.booking.impl;

import java.util.List;

import ru.practicum.shareit.booking.dto.BookingDto;

public interface BookingService {

    public BookingDto postBooking(Long userId, BookingDto bookingDto);

    public BookingDto patchBooking(Long userId, Long bookingId, boolean approved);

    public BookingDto getBooking(Long userId, Long bookingId);

    public List<BookingDto> getUserBookings(Long userId);

    public List<BookingDto> getOwnerBookings(Long ownerId);

    public List<BookingDto> getUserBookingsWithState(Long userId, String state);

    public List<BookingDto> getOwnerBookingsWithState(Long ownerId, String state);

}
