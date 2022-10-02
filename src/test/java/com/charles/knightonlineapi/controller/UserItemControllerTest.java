package com.charles.knightonlineapi.controller;

import com.charles.knightonlineapi.KnightOnlineApiApplication;
import com.charles.knightonlineapi.commons.CommonIntTest;
import com.charles.knightonlineapi.commons.annotations.user.RunWithMockCustomUser;
import com.charles.knightonlineapi.enums.RoleEnum;
import com.charles.knightonlineapi.model.dto.ItemDTO;
import com.charles.knightonlineapi.model.dto.UserDTO;
import com.charles.knightonlineapi.model.dto.UserItemDTO;
import com.charles.knightonlineapi.service.mock.ItemMock;
import com.charles.knightonlineapi.service.mock.UserItemMock;
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
class UserItemControllerTest extends CommonIntTest {

    private static final String BASE_URL = "/user/item";

    @Autowired
    private UserMock userMock;

    @Autowired
    private ItemMock itemMock;

    @Autowired
    private UserItemMock userItemMock;

    @BeforeEach
    public void init() {
        UserDTO userDTO = userMock.getUserMock();
        userMock.createUserWithRole(userDTO, RoleEnum.ADMIN);
        ItemDTO itemDTO = itemMock.getItemMock(TestUtils.generateRandomString());
        itemMock.createItem(itemDTO);
        UserItemDTO userItemDTO = new UserItemDTO();
        userItemDTO.setUserId(userMock.getUser());
        userItemDTO.setItemId(itemMock.getItem());
        userItemMock.createUserItem(userItemDTO);
    }

    @AfterEach
    public void close() {
        itemMock.deleteAllItem();
        userItemMock.deleteAllUserItem();
        userMock.deleteAllUser();
    }

    @Order(1)
    @Test
    @DisplayName("Should create user item")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldCreateUserItem() throws Exception {
        userItemMock.deleteAllUserItem();
        UserItemDTO dto = userItemMock.getUserItemMock(userMock.getUser(), itemMock.getItem());

        this.getMockMvc()
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("user_item_success"));
    }

    @Test
    @DisplayName("Should get user item")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetUserItem() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, userItemMock.getUserItem().toString())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get all user items")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetAllUserItems() throws Exception {
        this.getMockMvc()
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Should delete user item")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldDeleteUserItem() throws Exception {
        this.getMockMvc()
                .perform(delete(buildUrl(BASE_URL, userItemMock.getUserItem().toString())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("user_item_success"));
    }
}
