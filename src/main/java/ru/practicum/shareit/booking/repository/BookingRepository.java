package ru.practicum.shareit.booking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.practicum.shareit.booking.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "select b from bookings b where b.item.owner.id=?1 and b.id=?2")
    public Optional<Booking> findByOwner(Long ownerId, Long bookingId);

    @Query(value = "select b from bookings b where (b.item.owner.id=?1 or b.booker.id=?1) and b.id=?2")
    public Booking findByUser(Long userId, Long bookingId);

    @Query(value = "select b from bookings b where b.booker.id=?1 order by b.id desc")
    public Optional<List<Booking>> findUserBookings(Long userId);

    @Query(value = "select b from bookings b where b.item.owner.id=?1 order by b.id desc")
    public Optional<List<Booking>> findOwnerBookings(Long ownerId);

    @Query(value = "select b from bookings b where b.booker.id=?1 and b.start > current_timestamp order by b.id desc")
    public List<Booking> findFutureUserBookings(Long userId);

    @Query(value = "select b from bookings b where b.item.owner.id=?1 and b.start > current_timestamp order by b.id desc")
    public List<Booking> findFutureOwnerBookings(Long ownerId);

    @Query(value = "select b from bookings b where b.booker.id=?1 and b.start < current_timestamp and b.end>current_timestamp order by b.id")
    public List<Booking> findCurrentUserBookings(Long userId);

    @Query(value = "select b from bookings b where b.item.owner.id=?1 and b.start < current_timestamp and b.end>current_timestamp order by b.id")
    public List<Booking> findCurrentOwnerBookings(Long ownerId);

    @Query(value = "select b from bookings b where b.booker.id=?1 and b.end < current_timestamp order by b.id desc")
    public List<Booking> findPastUserBookings(Long userId);

    @Query(value = "select b from bookings b where b.item.owner.id=?1 and b.end < current_timestamp order by b.id desc")
    public List<Booking> findPastOwnerBookings(Long ownerId);

    @Query(value = "select b from bookings b where b.booker.id=?1 and b.status like concat('%', ?2, '%') order by b.id")
    public List<Booking> findUserBookingsWithStatus(Long userId, String state);

    @Query(value = "select b from bookings b where b.item.owner.id=?1 and b.status like concat('%', ?2, '%') order by b.id")
    public List<Booking> findOwnerBookingsWithStatus(Long ownerId, String state);
}
