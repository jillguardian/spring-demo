package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto save(UserDto user) {
        User unsaved = userMapper.toEntity(user);
        User saved = userRepository.save(unsaved);
        return userMapper.toDto(saved);
    }

    @Override
    public Optional<UserDto> findById(long id) {
        Optional<User> found = userRepository.findById(id);
        return found.map(userMapper::toDto);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> all = userRepository.findAll(pageable);
        return all.map(userMapper::toDto);
    }

    @Override
    public Optional<UserDto> deleteById(long id) {
        Optional<User> found = userRepository.findById(id);
        if (found.isPresent()) {
            UserDto dto = userMapper.toDto(found.get());
            userRepository.deleteById(id);
            return Optional.of(dto);
        }
        return Optional.empty();
    }

}
