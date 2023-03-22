package ua.foxminded.warehouse.integration_tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.foxminded.warehouse.AppSpringBoot;
import ua.foxminded.warehouse.controller.urls.RestUrl;
import ua.foxminded.warehouse.dao.ItemDAO;
import ua.foxminded.warehouse.dao.StockBalanceDAO;
import ua.foxminded.warehouse.service.entities.Item;
import ua.foxminded.warehouse.service.entities.StockBalance;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { AppSpringBoot.class })
@Sql({ "/stock_test.sql" })
@TestInstance(Lifecycle.PER_CLASS)
class StockBalanceControllerSytemTest {

    private MockMvc mockMvc;
    private final String date = "2022-08-25";
    private final BigDecimal stock_remainder = BigDecimal.valueOf(104);
    private final int notExistedId = 100;
    private final String contentType = MediaType.APPLICATION_JSON_VALUE;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private StockBalanceDAO repository;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private StockBalance createTestBalance() {
        return repository.findAll().get(0);
    }

    @Test
    void findCurrentBalance_returnStatus200() throws Exception {
        Item item = itemDAO.findAll().get(0);

        ResultActions response = mockMvc.perform(get(RestUrl.GET_CURRENT_BALANCE)
                .contentType(contentType).content(objectMapper.writeValueAsString(item)));
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data.stockRemainder").value(stock_remainder))
                .andExpect(jsonPath("$.data.date").value(date));
    }

    @Test
    void findById_returnStatus200() throws Exception {
        int id = createTestBalance().getId();

        ResultActions response = mockMvc.perform(get(RestUrl.GET_BALANCE_BY_ID, id));
        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.message", containsString("success")));
    }

    @Test
    void findById_returnStatus404() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.GET_BALANCE_BY_ID, notExistedId));
        response.andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("ERROR")));
    }

    @Test
    void findBalanceByDate_returnStatus200() throws Exception {
        ResultActions response = mockMvc
                .perform(get(RestUrl.GET_BALANCE_BY_DATE).contentType(MediaType.APPLICATION_JSON).content(date));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.data[0].stockRemainder").value(stock_remainder))
                .andExpect(jsonPath("$.data[0].date").value(date.toString()));
    }
}