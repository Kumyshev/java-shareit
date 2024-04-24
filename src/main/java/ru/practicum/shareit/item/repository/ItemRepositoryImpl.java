package ru.practicum.shareit.item.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import ru.practicum.shareit.item.impl.ItemRepository;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();

    private static Long counter = 1L;

    public Map<Long, Item> itemMap() {
        return items;
    }

    @Override
    public Collection<Item> findAll() {
        return items.values();
    }

    @Override
    public Item findById(Long itemId) {
        var item = items.values().stream().filter(i -> i.getId() == itemId).findAny().orElse(null);
        return item;
    }

    @Override
    public Item saveItem(Item item, Long userId) {
        if (item.getId() == null)
            item.setId(counter++);
        items.put(userId, item);
        return item;
    }

    @Override
    public void deleteItemById(Long itemId) {
        items.remove(itemId);
    }

    @Override
    public void deleteAll() {
        items.clear();
    }

    @Override
    public Collection<Item> findByUserId(Long userId) {
        Collection<Item> itemList = new ArrayList<>();
        itemList.add(items.get(userId));
        return itemList;
    }

}
