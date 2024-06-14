package ru.practicum.shareit.booking;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.impl.BookingService;
import ru.practicum.shareit.booking.validator.StatusConstraint;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;

@Validated
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto postBooking(@Validated(BookingDto.Create.class) @RequestBody BookingDto bookingDto,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.postBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto patchBooking(@RequestParam boolean approved, @PathVariable("bookingId") Long bookingId,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.patchBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@PathVariable("bookingId") Long bookingId,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getBooking(userId, bookingId);
    }

    @Validated
    @GetMapping
    public List<BookingDto> getUserBookings(
            @RequestHeader(value = "X-Sharer-User-Id") Long userId,
            @RequestParam(required = false) @StatusConstraint(enumClass = Status.class) String state,
            @RequestParam(required = false, name = "from") @Min(0) Integer from,
            @RequestParam(required = false, name = "size") @Min(1) Integer size) {
        if (state != null && !state.equals("ALL"))
            return bookingService.getUserBookingsWithState(userId, state);
        return bookingService.getUserBookings(userId, from, size);
    }

    @Validated
    @GetMapping("/owner")
    public List<BookingDto> getOwnerBookings(
            @RequestHeader(value = "X-Sharer-User-Id") Long userId,
            @RequestParam(required = false) @StatusConstraint(enumClass = Status.class) String state,
            @RequestParam(required = false, name = "from") @Min(0) Integer from,
            @RequestParam(required = false, name = "size") @Min(1) Integer size) {
        if (state != null && !state.equals("ALL"))
            return bookingService.getOwnerBookingsWithState(userId, state);
        return bookingService.getOwnerBookings(userId, from, size);
    }

}
