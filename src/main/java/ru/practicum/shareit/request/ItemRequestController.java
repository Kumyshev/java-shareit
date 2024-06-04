package ru.practicum.shareit.request;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.impl.RequestService;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class ItemRequestController {

    private final RequestService requestService;

    @PostMapping
    public ItemRequestDto postItemRequest(
            @Validated(ItemRequestDto.Create.class) @RequestBody ItemRequestDto itemRequestDto,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestService.postItemRequest(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestDto> getRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestService.getRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequest(@PathVariable("requestId") Long requestId,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestService.getRequest(requestId, userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getRequests(@RequestParam(required = false, name = "from") @Min(0) Integer from,
            @RequestParam(required = false, name = "size") @Min(1) Integer size,
            @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return requestService.getRequest(userId, from, size);
    }

}
