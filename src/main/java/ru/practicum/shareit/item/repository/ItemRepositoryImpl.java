package ru.practicum.shareit.item.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.impl.ItemRepository;
import ru.practicum.shareit.item.model.Item;

@Component
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Collection<Item>> itemsMap = new HashMap<>();
    private final Map<Long, Item> itemMap = new HashMap<>();

    private Long counter = 1L;

    @Override
    public Collection<Item> findAll() {
        return itemsMap.values().stream().flatMap(s -> s.stream()).collect(Collectors.toList());
    }

    @Override
    public Item findByItemId(Long itemId) {
        return itemMap.get(itemId);
    }

    @Override
    public Collection<Item> findByUserId(Long userId) {
        return itemsMap.get(userId);
    }

    @Override
    public Item saveItem(Item item) {
        if (item.getId() == null)
            item.setId(counter++);
        itemMap.put(item.getId(), item);
        itemsMap.computeIfAbsent(item.getUser().getId(), k -> new ArrayList<>()).add(item);
        return item;
    }
}
