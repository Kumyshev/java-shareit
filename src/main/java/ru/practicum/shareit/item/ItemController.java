package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.impl.ItemService;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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
    private final ItemService itemService;

    @GetMapping
    public Collection<ItemDto> findByUserId(@RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return itemService.findByUserId(userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> findByText(@RequestParam String text) {
        return itemService.findByText(text);
    }

    @GetMapping("{itemId}")
    public ItemDto findByItemId(@PathVariable("itemId") Long itemId) {
        return itemService.findByItemId(itemId);
    }

    @PostMapping
    public ItemDto saveItem(@Valid @RequestBody ItemDto itemDto,
            @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        itemDto.setUserId(userId);
        return itemService.saveItem(itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@Valid @RequestBody ItemUpdateDto itemUpdateDto, @PathVariable("itemId") Long itemId,
            @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        if (itemUpdateDto.getName() != null && itemUpdateDto.getName().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Некорректный запрос.");
        itemUpdateDto.setUserId(userId);
        return itemService.updateItem(itemUpdateDto, itemId);
    }
}
