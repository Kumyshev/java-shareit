package ru.practicum.shareit.comment.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.impl.CommentService;
import ru.practicum.shareit.comment.mapper.CommentMapper;
import ru.practicum.shareit.comment.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final CommentMapper commentMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CommentDto postComment(Long userId, Long itemId, CommentDto commentDto) {
        var booking = getBookerHasBooking(userId, itemId);
        if (booking != null) {
            var comment = commentMapper.toComment(commentDto);
            comment.setAuthor(booking.getBooker());
            comment.setItem(booking.getItem());
            return commentMapper.toCommentDto(commentRepository.save(comment));
        } else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Booking getBookerHasBooking(Long userId, Long itemId) {
        try {
            return entityManager
                    .createQuery(
                            "select b from bookings b where "
                                    + " b.booker.id=?1 and b.item.id=?2 and b.end < now()",
                            Booking.class)
                    .setParameter(1, userId).setParameter(2, itemId).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CommentDto> getComment(Long userId, Long itemId) {
        return commentRepository.findAllCommentsByItemId(itemId)
                .stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

}
