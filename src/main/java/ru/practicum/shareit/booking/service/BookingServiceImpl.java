package ru.practicum.shareit.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.Status;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.impl.BookingService;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.impl.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.impl.UserService;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final ItemService itemService;

    private final UserService userService;

    @Override
    public BookingDto postBooking(Long userId, BookingDto bookingDto) {
        User user = userService.findUser(userId);
        Item item = itemService.findItem(bookingDto.getItemId());
        if (item.getOwner().getId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!item.getAvailable() || bookingDto.getEnd().isBefore(bookingDto.getStart())
                || bookingDto.getEnd().equals(bookingDto.getStart()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Booking booking = bookingMapper.toBooking(bookingDto);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(Status.WAITING);
        return bookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto patchBooking(Long userId, Long bookingId, boolean approved) {
        Booking booking = bookingRepository.findByOwner(userId, bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (booking.getStatus().equals(Status.APPROVED))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (booking.getStatus().equals(Status.WAITING) && approved == true)
            booking.setStatus(Status.APPROVED);
        else if (booking.getStatus().equals(Status.WAITING) && approved == false)
            booking.setStatus(Status.REJECTED);

        return bookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getBooking(Long userId, Long bookingId) {
        try {
            return bookingMapper.toBookingDto(bookingRepository.findByUser(userId, bookingId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<BookingDto> getUserBookings(Long userId) {
        List<Booking> bookings = bookingRepository.findUserBookings(userId)
                .filter(b -> !b.isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return bookings.stream()
                .map(bookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getOwnerBookings(Long ownerId) {
        List<Booking> bookings = bookingRepository.findOwnerBookings(ownerId)
                .filter(b -> !b.isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return bookings.stream()
                .map(bookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getUserBookingsWithState(Long userId, String state) {
        List<Booking> bookings = new ArrayList<>();
        if (state.equals("FUTURE"))
            bookings.addAll(bookingRepository.findFutureUserBookings(userId));
        else if (state.equals("CURRENT"))
            bookings.addAll(bookingRepository.findCurrentUserBookings(userId));
        else if (state.equals("PAST"))
            bookings.addAll(bookingRepository.findPastUserBookings(userId));
        else if (!state.isEmpty())
            bookings.addAll(bookingRepository.findUserBookingsWithStatus(userId, state));
        return bookings.stream()
                .map(bookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getOwnerBookingsWithState(Long ownerId, String state) {
        List<Booking> bookings = new ArrayList<>();
        if (state.equals("FUTURE"))
            bookings.addAll(bookingRepository.findFutureOwnerBookings(ownerId));
        else if (state.equals("CURRENT"))
            bookings.addAll(bookingRepository.findCurrentOwnerBookings(ownerId));
        else if (state.equals("PAST"))
            bookings.addAll(bookingRepository.findPastOwnerBookings(ownerId));
        else if (!state.isEmpty())
            bookings.addAll(bookingRepository.findOwnerBookingsWithStatus(ownerId, state));
        return bookings.stream()
                .map(bookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

}
