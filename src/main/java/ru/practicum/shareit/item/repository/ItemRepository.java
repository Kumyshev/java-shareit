package ru.practicum.shareit.item.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, BookingInfo {

    @Query(value = "select i from items i, users u where i.id=?1 and u.id=?2")
    public Item findItemByIdAndOwnerId(Long itemId, Long ownerId);

    @Query(value = "select i from items i where i.owner.id=?1 order by i.id")
    public List<Item> findAllOwnerItems(Long ownerId);

    @Query(value = " select i from items i where upper(i.name) like upper(concat('%',?1, '%')) or upper(i.description) like upper(concat('%',?1, '%'))")
    public List<Item> findItemByText(String text);

    @Query(value = "select i from items i where i.available = true")
    public Optional<Item> findAvailableItem(Long itemId);
}
