package ru.practicum.shareit.item.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.impl.ItemRepository;
import ru.practicum.shareit.item.impl.ItemService;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.impl.UserRepository;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto findByItemId(Long itemId) {
        return itemMapper.toDto(itemRepository.findByItemId(itemId));
    }

    @Override
    public ItemDto saveItem(ItemDto itemDto, Long userId) {
        userRepository.findById(userId);
        return itemMapper.toDto(itemRepository.saveItem(itemMapper.toItem(itemDto), userId));
    }

    @Override
    public ItemDto updateItem(ItemUpdateDto itemUpdateDto, Long itemId, Long userId) {
        userRepository.findById(userId);
        var items = itemRepository.findByUserId(userId);
        if (items == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с таким id не найден.");
        var item = itemRepository.findByItemId(itemId);
        if (!items.contains(item))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Доступ запрещён.");
        itemMapper.toUpdateItem(itemUpdateDto, item);
        return itemMapper.toDto(item);
    }

    @Override
    public Collection<ItemDto> findByUserId(Long userId) {
        return itemRepository.findByUserId(userId).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> findByText(String text) {
        if (text.isBlank())
            return new ArrayList<>();
        return itemRepository.findAll().stream()
                .filter(item -> item.getAvailable() == true)
                .filter(item -> StringUtils.containsIgnoreCase(item.getName() + " " + item.getDescription(), text))
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

}
