package com.example.demo.user;

import com.example.demo.common.GetResourceContent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("POST")
    class Post {

        @Test
        @DisplayName("When email is not provided, then return 400")
        void missingEmail() throws Exception {
            String requestBody = GetResourceContent.INSTANCE.apply("user/create/missing-email-request.json");
            String responseBody = GetResourceContent.INSTANCE.apply("user/create/missing-email-response.json");
            mockMvc.perform(post("/user")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(responseBody, true));
        }

        @Test
        @DisplayName("When ID is provided, then return 400")
        void unexpectedId() throws Exception {
            String requestBody = GetResourceContent.INSTANCE.apply("user/create/with-id-request.json");
            String responseBody = GetResourceContent.INSTANCE.apply("user/create/with-id-response.json");
            mockMvc.perform(post("/user")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(responseBody, true));
        }

        @Test
        @DisplayName("When valid, then invokes service to create user")
        void success() throws Exception {
            UserDto dtoFromRequest = UserDto.builder()
                    .email("takeittillyoumakeit@mailinator.com")
                    .password("$eCr3t")
                    .username("captainsparkles")
                    .build();
            UserDto dtoFromService = dtoFromRequest.toBuilder()
                    .id(487327423L)
                    .build();
            when(userService.save(dtoFromRequest)).thenReturn(dtoFromService);

            String requestBody = GetResourceContent.INSTANCE.apply("user/create/request.json");
            String responseBody = GetResourceContent.INSTANCE.apply("user/create/response.json");
            mockMvc.perform(post("/user")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(responseBody, true));
        }

    }

    @Nested
    @DisplayName("GET")
    class GetAll {

        @Test
        @DisplayName("Given pageable, then return paginated results")
        void success() throws Exception {
            Pageable pageable = PageRequest.of(0, 1);
            List<UserDto> content = List.of(
                    UserDto.builder()
                            .id(5928455L)
                            .username("mewmewmew")
                            .password("openS3SAM3!")
                            .email("anon@earth.org")
                            .build()
            );
            Page<UserDto> dtos = new PageImpl<>(content, pageable, 5);
            when(userService.findAll(pageable)).thenReturn(dtos);

            String responseBody = GetResourceContent.INSTANCE.apply("user/all.json");
            mockMvc.perform(get("/user")
                    .param("page", "0")
                    .param("size", "1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(responseBody, true));
        }

    }

}