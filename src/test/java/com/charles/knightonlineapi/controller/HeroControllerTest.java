package com.charles.knightonlineapi.controller;

import com.charles.knightonlineapi.KnightOnlineApiApplication;
import com.charles.knightonlineapi.commons.CommonIntTest;
import com.charles.knightonlineapi.commons.annotations.user.RunWithMockCustomUser;
import com.charles.knightonlineapi.enums.RoleEnum;
import com.charles.knightonlineapi.model.dto.HeroDTO;
import com.charles.knightonlineapi.model.dto.UserDTO;
import com.charles.knightonlineapi.service.mock.HeroMock;
import com.charles.knightonlineapi.service.mock.UserMock;
import com.charles.knightonlineapi.utils.SerializationUtils;
import com.charles.knightonlineapi.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWithMockCustomUser
@SpringBootTest(classes = KnightOnlineApiApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HeroControllerTest extends CommonIntTest {

    private static final String BASE_URL = "/hero";
    private String randomString;

    @Autowired
    private UserMock userMock;

    @Autowired
    private HeroMock heroMock;

    @BeforeEach
    public void init() {
        UserDTO userDTO = userMock.getUserMock();
        userMock.createUserWithRole(userDTO, RoleEnum.ADMIN);
        randomString = TestUtils.generateRandomString();
        HeroDTO heroDTO = heroMock.getHeroMock(randomString);
        heroMock.createHero(heroDTO);
    }

    @AfterEach
    public void close() {
        userMock.deleteAllUser();
        heroMock.deleteAllHero();
    }

    @Order(1)
    @Test
    @DisplayName("Should create hero")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldCreateHero() throws Exception {
        HeroDTO dto = heroMock.getHeroMock(TestUtils.generateRandomString());

        this.getMockMvc()
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("hero_success"));
    }

    @Test
    @DisplayName("Should update hero")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldUpdateHero() throws Exception {
        HeroDTO dto = new HeroDTO();
        dto.setId(heroMock.getHero());
        dto.setName(TestUtils.generateRandomString());
        dto.setImage(TestUtils.generateRandomString());

        this.getMockMvc()
                .perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("hero_success"));
    }

    @Test
    @DisplayName("Should get hero")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetHero() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, heroMock.getHero().toString())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get all heroes")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetAllHeroes() throws Exception {
        this.getMockMvc()
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Should search heroes")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldSearchHeroes() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, String.format("search?searchTerm=%s", randomString))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Should delete hero")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldDeleteHero() throws Exception {
        this.getMockMvc()
                .perform(delete(buildUrl(BASE_URL, heroMock.getHero().toString())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("hero_success"));
    }
}
