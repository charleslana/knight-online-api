package com.charles.knightonlineapi.controller;

import com.charles.knightonlineapi.KnightOnlineApiApplication;
import com.charles.knightonlineapi.commons.CommonIntTest;
import com.charles.knightonlineapi.commons.annotations.user.RunWithMockCustomUser;
import com.charles.knightonlineapi.enums.RoleEnum;
import com.charles.knightonlineapi.model.dto.ItemDTO;
import com.charles.knightonlineapi.model.dto.UserDTO;
import com.charles.knightonlineapi.service.mock.ItemMock;
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
class ItemControllerTest extends CommonIntTest {

    private static final String BASE_URL = "/item";
    private String randomString;

    @Autowired
    private UserMock userMock;

    @Autowired
    private ItemMock itemMock;

    @BeforeEach
    public void init() {
        UserDTO userDTO = userMock.getUserMock();
        userMock.createUserWithRole(userDTO, RoleEnum.ADMIN);
        randomString = TestUtils.generateRandomString();
        ItemDTO itemDTO = itemMock.getItemMock(randomString);
        itemMock.createItem(itemDTO);
    }

    @AfterEach
    public void close() {
        userMock.deleteAllUser();
        itemMock.deleteAllItem();
    }

    @Order(1)
    @Test
    @DisplayName("Should create item")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldCreateItem() throws Exception {
        ItemDTO dto = itemMock.getItemMock(TestUtils.generateRandomString());

        this.getMockMvc()
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("item_success"));
    }

    @Test
    @DisplayName("Should update item")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldUpdateItem() throws Exception {
        ItemDTO dto = new ItemDTO();
        dto.setId(itemMock.getItem());
        dto.setName(TestUtils.generateRandomString());
        dto.setImage(TestUtils.generateRandomString());
        dto.setLevel(1);

        this.getMockMvc()
                .perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("item_success"));
    }

    @Test
    @DisplayName("Should get item")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetItem() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, itemMock.getItem().toString())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get all items")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetAllItems() throws Exception {
        this.getMockMvc()
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Should search items")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldSearchItems() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, String.format("search?searchTerm=%s", randomString))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Should delete item")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldDeleteItem() throws Exception {
        this.getMockMvc()
                .perform(delete(buildUrl(BASE_URL, itemMock.getItem().toString())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("item_success"));
    }
}
