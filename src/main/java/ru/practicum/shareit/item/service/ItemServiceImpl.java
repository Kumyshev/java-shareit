package ru.practicum.shareit.item.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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
    public Item findByItemId(Long itemId) {
        return repository.findByItemId(itemId);
    }

    @Override
    public Item saveItem(Item item) {
        service.findById(item.getUserId());
        return repository.saveItem(item);
    }

    @Override
    public Item updateItem(ItemDto itemDto, Long itemId) {
        var userId = itemDto.getUserId();
        service.findById(userId);
        if (!repository.itemMap().containsKey(userId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с таким id не найден.");
        mapper.getConfiguration().setSkipNullEnabled(true);
        var item = findByItemId(itemId);
        mapper.map(itemDto, item);
        return item;
    }

    @Override
    public Collection<Item> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Collection<Item> findByText(String text) {
        if (text.isBlank())
            return new ArrayList<>();
        return repository.getItemCollection().stream()
                .filter(items -> StringUtils.containsIgnoreCase(items.toString(), text))
                .filter(items -> items.getAvailable() == true)
                .collect(Collectors.toList());
    }

}
