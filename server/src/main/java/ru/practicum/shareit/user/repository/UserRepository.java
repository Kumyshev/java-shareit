package ru.practicum.shareit.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.practicum.shareit.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from users u where u.id=?1")
    public User findUserById(Long id);

    @Query(value = "select u from users u")
    public List<User> findAllUsers();
}
