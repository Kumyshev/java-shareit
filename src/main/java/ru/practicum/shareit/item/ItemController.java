package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemServiceImpl itemService;

    @GetMapping
    public Collection<Item> findByUserId(@RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return itemService.findByUserId(userId);
    }

    @GetMapping("/search")
    public Collection<Item> findByText(@RequestParam String text) {
        return itemService.findByText(text);
    }

    @GetMapping("{itemId}")
    public Item findByItemId(@PathVariable("itemId") Long itemId) {
        return itemService.findByItemId(itemId);
    }

    @PostMapping
    public Item saveItem(@Valid @RequestBody Item item,
            @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        item.setUserId(userId);
        return itemService.saveItem(item);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@Valid @RequestBody ItemDto itemDto, @PathVariable("itemId") Long itemId,
            @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        itemDto.setUserId(userId);
        return itemService.updateItem(itemDto, itemId);
    }
}
