package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.impl.CommentService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.impl.ItemService;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private final CommentService commentService;

    @PostMapping
    public ItemDto postItem(@Validated(ItemDto.Create.class) @RequestBody ItemDto itemDto,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.postItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto postItem(@Validated(ItemDto.Update.class) @RequestBody ItemDto itemDto,
            @RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("itemId") Long itemId) {
        return itemService.patchItem(itemDto, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("itemId") Long itemId) {
        return itemService.getItem(userId, itemId);
    }

    @GetMapping
    public List<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItem(@RequestParam(name = "text") String text) {
        return itemService.getItem(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto postComment(@Validated(CommentDto.Create.class) @RequestBody CommentDto commentDto,
            @PathVariable("itemId") Long itemId,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return commentService.postComment(userId, itemId, commentDto);
    }

}
