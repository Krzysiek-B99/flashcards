package com.example.flashcards.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Test
    void shouldGet403Status() throws Exception {
        MvcResult login = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user2\",\"password\":\"haslo2\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");

        mockMvc.perform(get("/sets/1")
                        .header("Authorization","Bearer "+token))
                .andDo(print())
                .andExpect(status().is(403));

        mockMvc.perform(delete("/sets/2")
                        .header("Authorization","Bearer "+token))
                .andDo(print())
                .andExpect(status().is(403));

        mockMvc.perform(put("/sets/2")
                        .header("Authorization","Bearer "+token)
                        .content("set3"))
                .andDo(print())
                .andExpect(status().is(403));
    }
    @Test
    void shouldGetPublicSet() throws Exception {
        MvcResult login = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user2\",\"password\":\"haslo2\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");

        mockMvc.perform(get("/sets/2")
                        .header("Authorization","Bearer "+token))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string("{\"id\":2,\"name\":\"set2\",\"privacy\":false,\"flashcards\":[]}"));

    }
    @Test
    @Transactional
    void shouldGet200Status() throws Exception {
        MvcResult login = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user1\",\"password\":\"haslo1\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");

        mockMvc.perform(delete("/sets/2")
                        .header("Authorization","Bearer "+token))
                .andDo(print())
                .andExpect(status().is(200));
    }
    @Test
    @Transactional
    void shouldGet200StatusAndChangedSetName() throws Exception {
        MvcResult login = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user1\",\"password\":\"haslo1\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");

        mockMvc.perform(put("/sets/2")
                        .header("Authorization","Bearer "+token)
                        .content("set3"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string("{\"id\":2,\"name\":\"set3\",\"privacy\":false}"));
    }

}