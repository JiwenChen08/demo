package com.example.mockmvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//MockMvcBuilders.*;
//MockMvcRequestBuilders.*;
//MockMvcResultMatchers.*;
//MockMvcResultHandlers.*;

/**
 * 1. standaloneSetup
 * 2. webAppContextSetup
 * 3. common setup
 * 4. async request
 */


@SpringBootTest
@AutoConfigureMockMvc
public class StoreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setupStandalone(WebApplicationContext context) {

        // No need, since you have @AutoConfigureMockMvc
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }


    @Test
    public void TestStoreQuery() throws Exception {
        mockMvc.perform(get("/stores/{storeId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Berlin QuickLunch Store"))
                .andReturn();
    }

    @Test
    public void TestStoreCreate() throws Exception {

        mockMvc.perform(post("/stores/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                        {
                                          "storeNo": "ST-20250215",
                                          "name": "Munich City Center Store",
                                          "address": "Marienplatz 8, 80331 München, Germany",
                                          "email": "munich-center@quicklunch.com",
                                          "phone": "+49 89 98765432",
                                          "status": 0
                                        }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.phone").value("+49 89 98765432"));
    }


    @Test
    public void TestAsync() throws Exception {
        /*
        MvcResult body = mockMvc.perform(get("/stores/{storeId}", 1))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult("body"))
                .andReturn();

        mockMvc.perform(asyncDispatch(body))
                .andExpect(status().isOk())
                .andExpect(content().string("body"));
        */
    }


}
