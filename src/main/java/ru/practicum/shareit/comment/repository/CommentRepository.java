package ru.practicum.shareit.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.practicum.shareit.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from comments c")
    public List<Comment> findAllCommentsByItemId(Long itemId);

}
