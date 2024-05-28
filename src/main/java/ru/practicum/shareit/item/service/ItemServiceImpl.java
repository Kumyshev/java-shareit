package ru.practicum.shareit.item.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.impl.CommentService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.impl.ItemService;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    private final UserRepository userRepository;

    private final CommentService commentService;

    @Override
    public ItemDto postItem(ItemDto itemDto, Long ownerId) {
        try {
            Item item = itemMapper.toItem(itemDto);
            Optional<User> user = userRepository.findById(ownerId);
            item.setOwner(user.get());
            return itemMapper.toItemDto(itemRepository.save(item));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ItemDto patchItem(ItemDto itemDto, Long ownerId, Long itemId) {
        try {
            Item item = itemRepository.findItemByIdAndOwnerId(itemId, ownerId);
            item = itemMapper.toUpdateItem(item, itemDto);
            return itemMapper.toItemDto(itemRepository.save(item));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ItemDto getItem(Long userId, Long itemId) {
        List<CommentDto> data = commentService.getComment(userId, itemId);
        try {
            Item item = itemRepository.findItemByIdAndOwnerId(itemId, userId);
            ItemDto itemDto = itemMapper.toItemDto(item);
            itemDto.setLastBooking(itemRepository.findLastBooking(userId, itemId));
            itemDto.setNextBooking(itemRepository.findNextBooking(userId, itemId));
            itemDto.setComments(data);
            ;
            return itemDto;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ItemDto> getItems(Long ownerId) {
        List<ItemDto> items = itemRepository.findAllOwnerItems(ownerId)
                .stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());

        for (ItemDto itemDto : items) {
            itemDto.setNextBooking(itemRepository.findNextBooking(ownerId, itemDto.getId()));
            itemDto.setLastBooking(itemRepository.findLastBooking(ownerId, itemDto.getId()));
        }

        return items;
    }

    @Override
    public List<ItemDto> getItem(String text) {
        if (text.isEmpty())
            return new ArrayList<>();
        return itemRepository.findItemByText(text)
                .stream()
                .filter(i -> i.getAvailable() == true)
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Item findItem(Long itemId) {
        try {
            Optional<Item> item = itemRepository.findById(itemId);
            return item.get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
