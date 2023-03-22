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
import ua.foxminded.warehouse.dao.PartnerDAO;
import ua.foxminded.warehouse.service.entities.Partner;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { AppSpringBoot.class })
@Sql({ "/partner_test.sql" })
@TestInstance(Lifecycle.PER_CLASS)
class PartnerControllerSystemTest {

    private MockMvc mockMvc;
    private final String tin = "3350754345";
    private final String name = "partner1";
    private final int notExistedId = 100;
    private final String contentType = MediaType.APPLICATION_JSON_VALUE;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private PartnerDAO repository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private Partner createTestPartner() {
        return repository.findAll().get(0);
    }

    @Test
    void createPartner_whenCreatePartner_thenReturn201Created() throws JsonProcessingException, Exception {
        Partner partner = createTestPartner();
        repository.deleteAll();
        ResultActions response = mockMvc.perform(post(RestUrl.CREATE_PARTNER).contentType(contentType)
                .content(objectMapper.writeValueAsString(partner)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$", is("success")));
    }

    @Test
    void updatePartner_whenUpdatePartner_thenReturn200Ok() throws JsonProcessingException, Exception {
        Partner updatedPartner = createTestPartner();
        updatedPartner.setName(name + "test");

        ResultActions response = mockMvc.perform(put(RestUrl.UPDATE_PARTNER, updatedPartner.getId())
                .contentType(contentType).content(objectMapper.writeValueAsString(updatedPartner)));
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Partner was updated successfully!")));
    }

    @Test
    void deletePartner_whenDeletePartner_thenReturn200Ok() throws Exception {
        mockMvc.perform(delete(RestUrl.REMOVE_PARTNER, createTestPartner().getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("success")));
    }

    @Test
    void findById_whenGetExistingPartner_thenReturnStatus200() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_PARTNER_BY_ID, createTestPartner().getId()));
        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    void findById_whenGetNotExistingPartner_thenReturnStatus404() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_PARTNER_BY_ID, notExistedId));
        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void findAllPartners_thenReturnStatus200() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_ALL_CUSTOMERS));
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.data[0].tin").value(tin)).andExpect(jsonPath("$.data[0].name").value(name));
    }
}
