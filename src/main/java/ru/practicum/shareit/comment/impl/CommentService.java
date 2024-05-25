package ru.practicum.shareit.comment.impl;

import java.util.List;

import ru.practicum.shareit.comment.dto.CommentDto;

public interface CommentService {

    public CommentDto postComment(Long userId, Long itemId, CommentDto commentDto);

    public List<CommentDto> getComment(Long userId, Long itemId);
}
