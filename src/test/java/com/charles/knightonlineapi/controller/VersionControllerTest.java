package com.charles.knightonlineapi.controller;

import com.charles.knightonlineapi.KnightOnlineApiApplication;
import com.charles.knightonlineapi.commons.CommonIntTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KnightOnlineApiApplication.class)
class VersionControllerTest extends CommonIntTest {

    private static final String BASE_URL = "/version";

    @Test
    @DisplayName("Should get backend version")
    void shouldGetBackendVersion() throws Exception {
        this.getMockMvc()
                .perform(get(BASE_URL))
                .andExpect(status().isOk());
    }
}
