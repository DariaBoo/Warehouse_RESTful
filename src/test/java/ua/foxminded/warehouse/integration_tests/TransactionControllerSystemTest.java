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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.foxminded.warehouse.AppSpringBoot;
import ua.foxminded.warehouse.controller.urls.RestUrl;
import ua.foxminded.warehouse.dao.ItemDAO;
import ua.foxminded.warehouse.dao.TransactionDAO;
import ua.foxminded.warehouse.service.entities.Transaction;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { AppSpringBoot.class })
@Sql({ "/transaction_test.sql" })
@TestInstance(Lifecycle.PER_CLASS)
class TransactionControllerSystemTest {

    private MockMvc mockMvc;
    private final int notExistedId = 100;
    private final String date = "2022-08-22";
    private final String type = "IN";
    private final BigDecimal quantity = BigDecimal.TEN;
    private final String contentType = MediaType.APPLICATION_JSON_VALUE;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TransactionDAO repository;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private Transaction createTestTransaction() {
        return repository.findAll().get(0);
    }

    @Test
    void createTransaction_whenCreate_thenReturn201Created() throws JsonProcessingException, Exception {
        Transaction transaction = createTestTransaction();
        repository.deleteAll();

        ResultActions response = mockMvc.perform(post(RestUrl.CREATE_TRANSACTION).contentType(contentType)
                .content(objectMapper.writeValueAsString(transaction)));
        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$", is("success")));
    }

    @Test
    void updateTransaction_whenUpdate_thenReturn200Ok() throws JsonProcessingException, Exception {
        Transaction updateTransaction = createTestTransaction();
        updateTransaction.setQuantity(BigDecimal.TEN);

        ResultActions response = mockMvc.perform(put(RestUrl.UPDATE_TRANSACTION, updateTransaction.getId())
                .contentType(contentType).content(objectMapper.writeValueAsString(updateTransaction)));
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Group was updated successfully!")));
    }

    @Test
    void deleteTransaction_whenDelete_thenReturn200Ok() throws Exception {
        mockMvc.perform(delete(RestUrl.REMOVE_TRANSACTION, createTestTransaction().getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("success")));
    }

    @Test
    void findById_whenGetExistingTransaction_thenReturnStatus200() throws Exception {
        long id = createTestTransaction().getId();
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_TRANSACTION_BY_ID, id));
        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    void findById_whenGetNotExistingTransaction_thenReturnStatus404() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_TRANSACTION_BY_ID, notExistedId));
        response.andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("ERROR")));
    }

    @Test
    void findByDateAndItem_returnStatus200() throws Exception {
        String itemId = String.valueOf(itemDAO.findAll().get(0).getId());
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_TRANSACTION_BY_DATE_AND_ITEM).param("date", date).param("itemId", itemId));
        response.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.data[0].type").value(type))
                .andExpect(jsonPath("$.data[0].quantity").value(quantity));
    }

    @Test
    void findByDate_returnStatus200() throws Exception {
        ResultActions response = mockMvc.perform(get(RestUrl.FIND_TRANSACTION_BY_DATE).param("date", date));
        response.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.data[0].type").value(type))
                .andExpect(jsonPath("$.data[0].quantity").value(quantity));
    }
}
