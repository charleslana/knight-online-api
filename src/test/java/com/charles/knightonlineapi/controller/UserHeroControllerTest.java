package com.charles.knightonlineapi.controller;

import com.charles.knightonlineapi.KnightOnlineApiApplication;
import com.charles.knightonlineapi.commons.CommonIntTest;
import com.charles.knightonlineapi.commons.annotations.user.RunWithMockCustomUser;
import com.charles.knightonlineapi.enums.RoleEnum;
import com.charles.knightonlineapi.model.dto.HeroDTO;
import com.charles.knightonlineapi.model.dto.UserDTO;
import com.charles.knightonlineapi.model.dto.UserHeroDTO;
import com.charles.knightonlineapi.service.mock.HeroMock;
import com.charles.knightonlineapi.service.mock.UserHeroMock;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWithMockCustomUser
@SpringBootTest(classes = KnightOnlineApiApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserHeroControllerTest extends CommonIntTest {

    private static final String BASE_URL = "/user/hero";

    @Autowired
    private UserMock userMock;

    @Autowired
    private HeroMock heroMock;

    @Autowired
    private UserHeroMock userHeroMock;

    @BeforeEach
    public void init() {
        UserDTO userDTO = userMock.getUserMock();
        userMock.createUserWithRole(userDTO, RoleEnum.ADMIN);
        HeroDTO heroDTO = heroMock.getHeroMock(TestUtils.generateRandomString());
        heroMock.createHero(heroDTO);
        UserHeroDTO userHeroDTO = new UserHeroDTO();
        userHeroDTO.setUserId(userMock.getUser());
        userHeroDTO.setHeroId(heroMock.getHero());
        userHeroMock.createUserHero(userHeroDTO);
    }

    @AfterEach
    public void close() {
        heroMock.deleteAllHero();
        userHeroMock.deleteAllUserHero();
        userMock.deleteAllUser();
    }

    @Order(1)
    @Test
    @DisplayName("Should create user hero")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldCreateUserHero() throws Exception {
        userHeroMock.deleteAllUserHero();
        UserHeroDTO dto = userHeroMock.getUserHeroMock(userMock.getUser(), heroMock.getHero());

        this.getMockMvc()
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("user_hero_success"));
    }

    @Test
    @DisplayName("Should get user hero")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetUserHero() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, userHeroMock.getUserHero().toString())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get all user heroes")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetAllUserHeroes() throws Exception {
        this.getMockMvc()
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Should delete user hero")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldDeleteHero() throws Exception {
        this.getMockMvc()
                .perform(delete(buildUrl(BASE_URL, userHeroMock.getUserHero().toString())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("user_hero_success"));
    }
}
