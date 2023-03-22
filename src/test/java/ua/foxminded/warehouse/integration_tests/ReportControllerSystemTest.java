package ua.foxminded.warehouse.integration_tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import ua.foxminded.warehouse.AppSpringBoot;
import ua.foxminded.warehouse.controller.urls.RestUrl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { AppSpringBoot.class })
@Sql({ "/report_test.sql" })
@TestInstance(Lifecycle.PER_CLASS)
class ReportControllerSystemTest {
    
    private MockMvc mockMvc;
    private final String dateFrom = "2022-08-22";
    private final String dateTo = "2022-08-23";
    @Autowired
    private WebApplicationContext context;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();  
    }

    @Test
    void getTotalReport() throws JsonProcessingException, Exception {     
        ResultActions response = mockMvc.perform(get(RestUrl.GET_TOTAL_REPORT).param("dateFrom", dateFrom).param("dateTo", dateTo));                        
        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.message", containsString("success")));
    }
    
    @Test
    void getItemReport() throws JsonProcessingException, Exception {       
        ResultActions response = mockMvc.perform(get(RestUrl.GET_ITEM_REPORT).param("dateFrom", dateFrom).param("dateTo", dateTo).param("itemId", "1"));                        
        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.message", containsString("success")));
    }
}