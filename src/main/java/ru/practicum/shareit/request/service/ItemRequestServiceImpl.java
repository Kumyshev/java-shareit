package ru.practicum.shareit.request.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.impl.ItemRequestService;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.impl.UserService;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestMapper requestMapper;
    private final ItemRequestRepository requestRepository;

    private final UserService userService;

    @Override
    public ItemRequestDto postItemRequest(ItemRequestDto itemRequestDto, Long userId) {
        User user = userService.findUser(userId);
        ItemRequest itemRequest = requestMapper.toItemRequest(itemRequestDto);
        itemRequest.setRequestor(user);
        itemRequest.setCreated(LocalDateTime.now());
        return requestMapper.toItemRequestDto(requestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> getRequests(Long userId) {
        userService.findUser(userId);
        return requestRepository
                .findItemRequestsByRequestor_id(userId)
                .stream()
                .map(requestMapper::toItemRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequest findRequest(Long requestId) {
        ItemRequest itemRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return itemRequest;
    }

    @Override
    public ItemRequestDto getRequest(Long requestId, Long userId) {
        userService.findUser(userId);
        ItemRequestDto itemRequestDto = requestMapper.toItemRequestDto(findRequest(requestId));
        return itemRequestDto;
    }

    @Override
    public List<ItemRequestDto> getRequest(Long userId, Integer from, Integer size) {
        userService.findUser(userId);
        if (from == null)
            return requestRepository
                    .findItemRequestsByUser(userId)
                    .stream()
                    .map(requestMapper::toItemRequestDto)
                    .collect(Collectors.toList());

        return requestRepository
                .findItemRequestsByUser(userId)
                .stream()
                .map(requestMapper::toItemRequestDto)
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

}
