package ua.foxminded.warehouse.integration_tests;

import static org.hamcrest.CoreMatchers.containsString;
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
import ua.foxminded.warehouse.dao.CategoryDAO;
import ua.foxminded.warehouse.service.entities.Category;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { AppSpringBoot.class })
@Sql({ "/category_test.sql" })
@TestInstance(Lifecycle.PER_CLASS)
class CategoryControllerSystemTest {

    private MockMvc mockMvc;
    private final String name = "category1";
    private final int notExistedId = 100;
    private final String contentType = MediaType.APPLICATION_JSON_VALUE;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private CategoryDAO categoryDao;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private Category createTestCategory() {
        return categoryDao.findAll().get(0);
    }

    @Test
    void createCategory_whenCreateCategory_thenReturn201Created() throws JsonProcessingException, Exception {
        Category category = createTestCategory();
        categoryDao.deleteAll();
        
        ResultActions response = mockMvc.perform(post(RestUrl.CREATE_CATEGORY).contentType(contentType)
                .content(objectMapper.writeValueAsString(category)));
        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$", is("Category was created successfully!")));
    }

    @Test
    void updateCategory_whenUpdateCategory_thenReturn200Ok() throws JsonProcessingException, Exception {
        Category updatedGroup = createTestCategory();
        updatedGroup.setName(name + "test");

        ResultActions response = mockMvc.perform(put(RestUrl.UPDATE_CATEGORY, updatedGroup.getId())
                .contentType(contentType).content(objectMapper.writeValueAsString(updatedGroup)));
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Category was updated successfully!")));
    }

    @Test
    void deleteCategory_whenDeleteCategory_thenReturn204() throws Exception {
        mockMvc.perform(delete(RestUrl.REMOVE_CATEGORY, createTestCategory().getId())).andExpect(status().isNoContent())
                .andExpect(jsonPath("$", is("Category removed successfully!")));
    }

    @Test
    void findById_whenGetExistingCategory_thenReturnStatus200() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_CATEGORY_BY_ID, createTestCategory().getId()));
        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    void findById_whenGetNotExistingCategory_thenReturnStatus404() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_CATEGORY_BY_ID, notExistedId));
        response.andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("No category with id " + notExistedId)));

    }

    @Test
    void findAllCategories_thenReturnStatus200() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_ALL_CATEGORIES));
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.dataList.size()").value(4));
    }
}
