package ru.practicum.shareit.item.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.impl.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepositoryImpl;
import ru.practicum.shareit.user.service.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepositoryImpl repository;
    private final UserServiceImpl service;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Collection<Item> findAll() {
        return repository.findAll();
    }

    @Override
    public Item findById(Long itemId) {
        return repository.findById(itemId);
    }

    @Override
    public Item saveItem(Item item, Long userId) {
        service.findById(userId);
        return repository.saveItem(item, userId);
    }

    @Override
    public Item updateItem(ItemDto itemDto, Long itemId, Long userId) {
        service.findById(userId);
        if (!repository.itemMap().containsKey(userId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с таким id не найден.");
        mapper.getConfiguration().setSkipNullEnabled(true);
        var item = findById(itemId);
        var itemClone = Item.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
        mapper.map(itemDto, itemClone);
        return repository.saveItem(itemClone, userId);
    }

    @Override
    public void deleteItemById(Long itemId) {
        repository.deleteItemById(itemId);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Collection<Item> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Collection<Item> findByText(String text) {
        if (text.isBlank())
            return new ArrayList<>();
        return repository.findAll().stream()
                .filter(items -> StringUtils.containsIgnoreCase(items.toString(), text))
                .filter(items -> items.getAvailable() == true)
                .toList();
    }

}
