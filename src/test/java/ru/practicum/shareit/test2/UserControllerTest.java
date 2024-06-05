package ru.practicum.shareit.test2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.impl.UserService;

@WebMvcTest(UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void getUser() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users");

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();

                assertEquals(200, result.getResponse().getStatus());
    }
}
