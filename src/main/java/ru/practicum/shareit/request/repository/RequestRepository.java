package ru.practicum.shareit.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.practicum.shareit.request.ItemRequest;

public interface RequestRepository extends JpaRepository<ItemRequest, Long> {

    public List<ItemRequest> findItemRequestsByRequestor_id(Long userId);

    @Query(value = "select r from requests r where r.requestor.id <> ?1")
    public List<ItemRequest> findItemRequestsByUser(Long userId);
}
