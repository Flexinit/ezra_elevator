package com.ezra.elevatorapi.controller;

import static org.mockito.Mockito.when;

import com.ezra.elevatorapi.service.QueryLogsService;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LogsController.class})
@ExtendWith(SpringExtension.class)
class LogsControllerTest {
    @Autowired
    private LogsController logsController;

    @MockBean
    private QueryLogsService queryLogsService;

    /**
     * Method under test: {@link LogsController#getDatabaseQueryLogs()}
     */
    @Test
    void testGetDatabaseQueryLogs() throws Exception {
        when(queryLogsService.getDatabaseQueryLogs()).thenReturn(Optional.of(new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/logs");
        MockMvcBuilders.standaloneSetup(logsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link LogsController#getElevatorRequests()}
     */
    @Test
    void testGetElevatorRequests() throws Exception {
        when(queryLogsService.getElevatorRequests()).thenReturn(Optional.of(new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/logs/elevatorRequests");
        MockMvcBuilders.standaloneSetup(logsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link LogsController#getElevatorRequests()}
     */
    @Test
    void testGetElevatorRequests2() throws Exception {
        when(queryLogsService.getElevatorRequests()).thenReturn(Optional.of(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/logs/elevatorRequests");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(logsController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

