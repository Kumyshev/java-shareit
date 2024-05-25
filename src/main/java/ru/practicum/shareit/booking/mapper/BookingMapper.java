package ru.practicum.shareit.booking.mapper;

import org.springframework.stereotype.Component;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.Booker;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.Item;

@Component
public class BookingMapper {

    public Booking toBooking(BookingDto bookingDto) {
        return Booking.builder()
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd()).build();

    }

    public BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .booker(new Booker(booking.getBooker().getId()))
                .item(new Item(booking.getItem().getId(), booking.getItem().getName()))
                .status(booking.getStatus().name()).build();

    }
}
