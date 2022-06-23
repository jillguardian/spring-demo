package com.example.demo.user;

import com.example.demo.common.ValidationGroup;
import com.example.demo.common.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class UserDto {

    @JsonView(View.Private.class)
    @NotNull(groups = ValidationGroup.Update.class)
    @Null(groups = ValidationGroup.Create.class)
    Long id;

    @JsonView(View.Public.class)
    @NotBlank
    String username;

    @JsonView(View.Public.class)
    @NotBlank
    @Email
    String email;

    @JsonView(View.Private.class)
    @NotBlank
    String password;

}
