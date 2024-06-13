package ru.practicum.shareit.item.impl;

import java.util.List;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public interface ItemService {

    public ItemDto postItem(ItemDto itemDto, Long ownerId);

    public ItemDto patchItem(ItemDto itemDto, Long ownerId, Long itemId);

    public ItemDto getItem(Long userId, Long itemId);

    public List<ItemDto> getItems(Long ownerId);

    public List<ItemDto> getItem(String text);

    public Item findItem(Long itemId);
}
