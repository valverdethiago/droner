package io.resin.droner.controller;

import io.resin.droner.DronerApplication;
import io.resin.droner.controller.impl.RestDroneControllerImpl;
import io.resin.droner.entities.Coordinate;
import io.resin.droner.entities.Drone;
import io.resin.droner.service.DroneService;
import io.resin.droner.service.EntityNotFoundException;
import io.resin.droner.util.JsonHelper;
import io.resin.droner.util.MockHelper;
import io.resin.droner.util.TestConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.swing.text.html.parser.Entity;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DronerApplication.class)
@EnableAutoConfiguration
public class RestDroneControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RestDroneControllerImpl service;
    @Mock
    private DroneService droneService;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(service)
                .build();
    }

    @Test
    public void shouldRegister() throws Exception {
        //Arrange
        when(this.droneService.registerDrone()).thenReturn(MockHelper.mockDrone());
        //Act
        mockMvc.perform(put("/api/drone").contentType(MediaType.APPLICATION_JSON_VALUE))
                //Assert
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(TestConstants.DEFAULT_DRONE_ID.toString())));
    }

    @Test
    public void shouldUpdateCoordinates() throws Exception {
        //Arrange
        UUID id = UUID.randomUUID();
        String url = String.format("/api/drone/update/%s", id.toString());
        Coordinate coordinate = MockHelper.mockCoordinate();
        //Act
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonHelper.convertObjectToJsonString(coordinate)))
                //Assert
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnErrorWhenUpdatingInvalidDrone() throws Exception {
        //Arrange
        UUID id = UUID.randomUUID();
        String url = String.format("/api/drone/update/%s", id.toString());
        Coordinate coordinate = MockHelper.mockCoordinate();
        String json = JsonHelper.convertObjectToJsonString(coordinate);
        doThrow(EntityNotFoundException.class)
                .when(droneService)
                .updateCoordinates(Mockito.any(UUID.class), Mockito.any(Coordinate.class));
        //Act
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                //Assert
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void shouldReturnNotFoundWhenBlankId() throws Exception {
        //Arrange
        Coordinate coordinate = MockHelper.mockCoordinate();
        String json = JsonHelper.convertObjectToJsonString(coordinate);
        //Act
        mockMvc.perform(post("/api/drone/update/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                //Assert
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void shouldReturnDroneList() throws Exception {
        //Arrange
        final int expectedListSize = 10;
        List<Drone> expectedDroneList = MockHelper.mockDroneList(expectedListSize);
        when(this.droneService.listAll()).thenReturn(expectedDroneList);
        //Act
        mockMvc.perform(get("/api/drone/all"))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedListSize)));
    }

    @Test
    public void shouldReturnEmptyDroneList() throws Exception {
        //Arrange
        when(this.droneService.listAll()).thenReturn(Collections.emptyList());
        //Act
        mockMvc.perform(get("/api/drone/all"))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
