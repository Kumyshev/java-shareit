package ru.practicum.shareit.request.controller;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> postItemRequest(@RequestBody ItemRequestDto itemRequestDto,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestClient.postItemRequest(itemRequestDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestClient.getRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@PathVariable("requestId") Long requestId,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestClient.getRequest(requestId, userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getRequests(@RequestParam(required = false, name = "from") @Min(0) Integer from,
            @RequestParam(required = false, name = "size") @Min(1) Integer size,
            @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return requestClient.getRequests(userId, from, size);
    }
}