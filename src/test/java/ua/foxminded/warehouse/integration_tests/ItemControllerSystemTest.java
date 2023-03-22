package ua.foxminded.warehouse.integration_tests;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.foxminded.warehouse.AppSpringBoot;
import ua.foxminded.warehouse.controller.urls.RestUrl;
import ua.foxminded.warehouse.dao.ItemDAO;
import ua.foxminded.warehouse.service.entities.Item;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { AppSpringBoot.class })
@Sql({ "/item_test.sql" })
@TestInstance(Lifecycle.PER_CLASS)
class ItemControllerSystemTest {

    private MockMvc mockMvc;
    private final String code = "8830329807";
    private final String name = "Product A";
    private final int notExistedId = 100;
    private final String contentType = MediaType.APPLICATION_JSON_VALUE;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ItemDAO repository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private Item createTestItem() {
        return repository.findAll().get(0);
    }

    @Test
    void createItem_whenCreateItem_thenReturn201Created() throws JsonProcessingException, Exception {
        Item item = createTestItem();
        repository.deleteAll();

        ResultActions response = mockMvc.perform(post(RestUrl.CREATE_ITEM).contentType(contentType)
                .content(objectMapper.writeValueAsString(item)));
        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$", is("Item was created successfully!")));
    }

    @Test
    void updateItem_whenUpdateItem_thenReturn200Ok() throws JsonProcessingException, Exception {
        Item updatedItem = createTestItem();
        updatedItem.setName(name + "test");

        ResultActions response = mockMvc.perform(put(RestUrl.UPDATE_ITEM, updatedItem.getId())
                .contentType(contentType).content(objectMapper.writeValueAsString(updatedItem)));
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Item was updated successfully!")));
    }

    @Test
    void deleteItem_whenDeleteItem_thenReturn200Ok() throws Exception {
        mockMvc.perform(delete(RestUrl.REMOVE_ITEM, createTestItem().getId())).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("success")));
    }

    @Test
    void findById_whenGetExistingItem_thenReturnStatus200() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_ITEM_BY_ID, createTestItem().getId()));
        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    void findById_whenGetNotExistingItem_thenReturnStatus404() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_ITEM_BY_ID, notExistedId));
        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void findAllItems_thenReturnStatus200() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_ALL_ITEMS));
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[0].sku").value(code)).andExpect(jsonPath("$.[0].name").value(name));
    }
}