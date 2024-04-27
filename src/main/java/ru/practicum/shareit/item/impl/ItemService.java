package ru.practicum.shareit.item.impl;

import java.util.Collection;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

public interface ItemService {

    ItemDto findByItemId(Long itemId);

    Collection<ItemDto> findByUserId(Long userId);

    Collection<ItemDto> findByText(String text);

    ItemDto saveItem(ItemDto itemDto);

    ItemDto updateItem(ItemUpdateDto itemUpdateDto, Long itemId);
}
