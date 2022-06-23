package com.example.demo.user;

import com.example.demo.common.Page;
import com.example.demo.common.ValidationGroup;
import com.example.demo.common.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.Public.class)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(
            @RequestBody
            @Validated({ Default.class, ValidationGroup.Create.class })
            @JsonView(View.Private.class) UserDto dto
    ) {
        UserDto sanitizedDto = dto.toBuilder().id(null).build();
        return userService.save(sanitizedDto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.Public.class)
    public UserDto update(
            @RequestBody
            @Validated({ Default.class, ValidationGroup.Update.class })
            @JsonView(View.Private.class) UserDto dto
    ) {
        return userService.save(dto.toBuilder().build());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.Private.class)
    public ResponseEntity<UserDto> findById(@PathVariable long id) {
        Optional<UserDto> found = userService.findById(id);
        return found.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.Public.class)
    public Page<UserDto> findAll(Pageable pageable) {
        return new Page<>(userService.findAll(pageable));
    }

}
