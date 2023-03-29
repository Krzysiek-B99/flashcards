package com.example.flashcards.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetAllUserSets() throws Exception {
        MvcResult login = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user1\",\"password\":\"haslo1\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");

        mockMvc.perform(get("/sets")
                        .header("Authorization","Bearer "+token))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string("[{\"id\":2,\"name\":\"set2\",\"privacy\":false},{\"id\":1,\"name\":\"set1\",\"privacy\":true}]"));
    }
    @Test
    void shouldGetSingleSet() throws Exception {
        MvcResult login = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user1\",\"password\":\"haslo1\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");

        mockMvc.perform(get("/sets/1")
                        .header("Authorization","Bearer "+token))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string("{\"id\":1,\"name\":\"set1\",\"privacy\":true,\"flashcards\":[{\"id\":2,\"front\":\"dom\",\"back\":\"house\",\"level\":1,\"repeatTime\":\"2023-03-16T17:04:55.18331\"},{\"id\":1,\"front\":\"jablko\",\"back\":\"apple\",\"level\":1,\"repeatTime\":\"2023-03-25T18:34:40.774875\"}]}"));
    }

}