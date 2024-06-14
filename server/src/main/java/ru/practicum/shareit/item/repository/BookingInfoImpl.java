package ru.practicum.shareit.item.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.practicum.shareit.item.model.Booking;

public class BookingInfoImpl implements BookingInfo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Booking findLastBooking(Long userId, Long itemId) {
        try {
            return entityManager
                    .createQuery(
                            "select new ru.practicum.shareit.item.model.Booking (b.id , b.booker.id) "
                                    + " from bookings b where b.item.owner.id=?1 and b.item.id=?2 "
                                    + " and b.start <= current_timestamp order by b.start desc",
                            Booking.class)
                    .setParameter(1, userId).setParameter(2, itemId).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Booking findNextBooking(Long userId, Long itemId) {
        try {
            return entityManager
                    .createQuery(
                            "select new ru.practicum.shareit.item.model.Booking (b.id , b.booker.id) "
                                    + " from bookings b where b.item.owner.id=?1 and b.item.id=?2 "
                                    + " and b.status like '%APPROVED%'"
                                    + " and b.start > current_timestamp order by b.start asc",
                            Booking.class)
                    .setParameter(1, userId).setParameter(2, itemId).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
