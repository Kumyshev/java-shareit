package ru.practicum.shareit.item.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ru.practicum.shareit.item.impl.ItemRepository;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Collection<Item>> items = new HashMap<>();

    private static Long counter = 1L;

    public Collection<Item> getItemCollection() {
        var itemList = items.values().stream().flatMap(s -> s.stream()).collect(Collectors.toList());
        return itemList;
    }

    public Map<Long, Collection<Item>> itemMap() {
        return items;
    }

    @Override
    public Item findByItemId(Long itemId) {
        return getItemCollection().stream().filter(i -> i.getId() == itemId).findAny()
                .orElse(null);
    }

    @Override
    public Collection<Item> findByUserId(Long userId) {
        return items.get(userId);
    }

    @Override
    public Item saveItem(Item item) {
        if (item.getId() == null)
            item.setId(counter++);
        var userId = item.getUserId();
        if (!items.containsKey(userId))
            items.put(userId, new ArrayList<>());
        items.get(userId).add(item);
        return item;
    }
}
