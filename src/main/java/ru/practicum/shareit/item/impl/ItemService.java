package ru.practicum.shareit.item.impl;

import java.util.Collection;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public interface ItemService {

    Collection<Item> findAll();

    Item findById(Long itemId);

    Collection<Item> findByUserId(Long userId);

    Collection<Item> findByText(String text);

    Item saveItem(Item item, Long userId);

    Item updateItem(ItemDto itemDto, Long itemId, Long userId);

    void deleteItemById(Long itemId);

    void deleteAll();
}
