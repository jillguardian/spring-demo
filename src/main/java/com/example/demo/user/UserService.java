package com.example.demo.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    UserDto save(UserDto user);
    Optional<UserDto> findById(long id);
    Page<UserDto> findAll(Pageable pageable);
    Optional<UserDto> deleteById(long id);
}
