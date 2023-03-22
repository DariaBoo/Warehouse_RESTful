package ua.foxminded.warehouse.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import config.TestConfig;
import ua.foxminded.warehouse.controller.urls.RestUrl;
import ua.foxminded.warehouse.service.CategoryService;
import ua.foxminded.warehouse.service.entities.Category;

@SpringJUnitWebConfig(TestConfig.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;
    @Autowired
    private ObjectMapper objectMapper;

    private Category category;

    @BeforeAll
    void setCategory() {
        category = new Category();
        category.setId(1);
        category.setName("test");
    }
    @Test
    void create() throws Exception {
        Mockito.when(categoryService.create(Mockito.any())).thenReturn(true);
        ResultActions response = mockMvc.perform(post(RestUrl.CREATE_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Category())));       
        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$", is("Category was created successfully!")));
    }
    
    @Test
    void findById() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("test");
        Mockito.when(categoryService.findById(Mockito.anyInt())).thenReturn(category);
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_CATEGORY_BY_ID, 1));
        response.andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    void findAll() throws Exception {
        List<Category> categories = new ArrayList<Category>();
        categories.add(category);

        Mockito.when(categoryService.findAll()).thenReturn(categories);
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_ALL_CATEGORIES));
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data.size()").value(categories.size()))
                .andExpect(jsonPath("$.data[0].name").value(category.getName()));
    }
}
