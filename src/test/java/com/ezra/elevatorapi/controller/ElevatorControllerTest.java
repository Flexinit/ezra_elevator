package com.ezra.elevatorapi.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ezra.elevatorapi.entity.Elevator;
import com.ezra.elevatorapi.entity.ElevatorLogs;
import com.ezra.elevatorapi.entity.ElevatorRequests;
import com.ezra.elevatorapi.service.ElevatorService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ElevatorController.class})
@ExtendWith(SpringExtension.class)
class ElevatorControllerTest {
    @Autowired
    private ElevatorController elevatorController;

    @MockBean
    private ElevatorService elevatorService;

    /**
     * Method under test: {@link ElevatorController#addNewElevator(Elevator)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddNewElevator() {


        ElevatorController elevatorController = new ElevatorController(new ElevatorService());

        Elevator elevator = new Elevator();
        elevator.setCapacity(10.0f);
        elevator.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        elevator.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        elevator.setId(123L);
        elevator.setName("Name");
        elevator.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        elevator.setUpdatedBy("2020-03-01");
        elevatorController.addNewElevator(elevator);
    }

    /**
     * Method under test: {@link ElevatorController#addNewElevator(Elevator)}
     */
    @Test
    void testAddNewElevator2() {


        Elevator elevator = new Elevator();
        elevator.setCapacity(10.0f);
        elevator.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        elevator.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        elevator.setId(123L);
        elevator.setName("Name");
        elevator.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        elevator.setUpdatedBy("2020-03-01");
        Optional<Elevator> ofResult = Optional.of(elevator);
        ElevatorService elevatorService = mock(ElevatorService.class);
        when(elevatorService.addNewElevator((Elevator) any())).thenReturn(ofResult);
        ElevatorController elevatorController = new ElevatorController(elevatorService);

        Elevator elevator1 = new Elevator();
        elevator1.setCapacity(10.0f);
        elevator1.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        elevator1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        elevator1.setId(123L);
        elevator1.setName("Name");
        elevator1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        elevator1.setUpdatedBy("2020-03-01");
        Optional<Elevator> actualAddNewElevatorResult = elevatorController.addNewElevator(elevator1);
        assertSame(ofResult, actualAddNewElevatorResult);
        assertTrue(actualAddNewElevatorResult.isPresent());
        verify(elevatorService).addNewElevator((Elevator) any());
    }

    /**
     * Method under test: {@link ElevatorController#getElevators()}
     */
    @Test
    void testGetElevators() throws Exception {
        when(elevatorService.getElevators()).thenReturn(Optional.of(new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/elevator");
        MockMvcBuilders.standaloneSetup(elevatorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ElevatorController#getElevators()}
     */
    @Test
    void testGetElevators2() throws Exception {
        when(elevatorService.getElevators()).thenReturn(Optional.of(new ArrayList<>()));
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(elevatorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ElevatorController#requestForElevator(ElevatorRequests)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRequestForElevator() {


        ElevatorController elevatorController = new ElevatorController(new ElevatorService());

        ElevatorRequests elevatorRequests = new ElevatorRequests();
        elevatorRequests.setBuildingId(123L);
        elevatorRequests.setCurrentFloorNumber(10);
        elevatorRequests.setDestinationFloorNumber(10);
        elevatorRequests.setElevatorId(123L);
        elevatorRequests.setId(123L);
        elevatorRequests.setRequestedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        elevatorRequests.setRequestedBy("Requested By");
        elevatorController.requestForElevator(elevatorRequests);
    }

    /**
     * Method under test: {@link ElevatorController#requestForElevator(ElevatorRequests)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRequestForElevator2() {


        ElevatorService elevatorService = new ElevatorService();
        elevatorService.initiateElevator = (Consumer<ElevatorLogs>) mock(Consumer.class);
        ElevatorController elevatorController = new ElevatorController(elevatorService);

        ElevatorRequests elevatorRequests = new ElevatorRequests();
        elevatorRequests.setBuildingId(123L);
        elevatorRequests.setCurrentFloorNumber(10);
        elevatorRequests.setDestinationFloorNumber(10);
        elevatorRequests.setElevatorId(123L);
        elevatorRequests.setId(123L);
        elevatorRequests.setRequestedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        elevatorRequests.setRequestedBy("Requested By");
        elevatorController.requestForElevator(elevatorRequests);
    }

    /**
     * Method under test: {@link ElevatorController#requestForElevator(ElevatorRequests)}
     */
    @Test
    void testRequestForElevator3() {


        ElevatorService elevatorService = mock(ElevatorService.class);
        Optional<ResponseEntity<ElevatorRequests>> ofResult = Optional.of(new ResponseEntity<>(HttpStatus.CONTINUE));
        when(elevatorService.requestForElevator((ElevatorRequests) any())).thenReturn(ofResult);
        ElevatorController elevatorController = new ElevatorController(elevatorService);

        ElevatorRequests elevatorRequests = new ElevatorRequests();
        elevatorRequests.setBuildingId(123L);
        elevatorRequests.setCurrentFloorNumber(10);
        elevatorRequests.setDestinationFloorNumber(10);
        elevatorRequests.setElevatorId(123L);
        elevatorRequests.setId(123L);
        elevatorRequests.setRequestedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        elevatorRequests.setRequestedBy("Requested By");
        Optional<ResponseEntity<ElevatorRequests>> actualRequestForElevatorResult = elevatorController
                .requestForElevator(elevatorRequests);
        assertSame(ofResult, actualRequestForElevatorResult);
        assertTrue(actualRequestForElevatorResult.isPresent());
        verify(elevatorService).requestForElevator((ElevatorRequests) any());
    }
}

