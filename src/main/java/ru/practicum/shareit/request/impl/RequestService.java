package ru.practicum.shareit.request.impl;

import java.util.List;

import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;

public interface RequestService {

    public ItemRequestDto postItemRequest(ItemRequestDto itemRequestDto, Long userId);

    public List<ItemRequestDto> getRequests(Long userId);

    public ItemRequest findRequest(Long requestId);

    public ItemRequestDto getRequest(Long requestId, Long userId);

    public List<ItemRequestDto> getRequest(Long userId, Integer from, Integer size);
}
