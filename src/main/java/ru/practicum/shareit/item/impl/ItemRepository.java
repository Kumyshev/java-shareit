package ru.practicum.shareit.item.impl;

import java.util.Collection;

import ru.practicum.shareit.item.model.Item;

public interface ItemRepository {

    Item findByItemId(Long itemId);

    Collection<Item> findByUserId(Long userId);

    Item saveItem(Item item);

}
