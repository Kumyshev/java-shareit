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
import ru.practicum.shareit.user.impl.UserRepository;

@Component
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Collection<Item>> itemsMap = new HashMap<>();
    private final Map<Long, Item> itemMap = new HashMap<>();
    private final UserRepository userRepository;

    private static Long counter = 1L;

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
    public Item saveItem(Item item, Long userId) {
        if (item.getId() == null)
            item.setId(counter++);
        var user = userRepository.findById(userId);
        item.setUser(user);
        itemMap.put(item.getId(), item);
        itemsMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(item);
        return item;
    }
}
