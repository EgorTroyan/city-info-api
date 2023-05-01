package ru.troyan.cityinfoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.troyan.cityinfoapi.model.CityInfoResponse;
import ru.troyan.cityinfoapi.model.ErrorResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class CityInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getCityInfo() throws Exception {
        MvcResult result = mockMvc.perform(get("/city-info?city=Moscow&country=Russia&latitude=55.1&longitude=37.2"))
                .andExpect(status().isOk())
                .andReturn();
        CityInfoResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), CityInfoResponse.class);
        Assertions.assertTrue(response.getExtract().length()>3);
        Assertions.assertTrue(response.getPhrase().contains("Did you know"));
    }

    @Test
    void getCityInfoWithWrongCoordinates() throws Exception {
        MvcResult result = mockMvc.perform(get("/city-info?city=Moscow&country=Russia&latitude=55.1&longitude=39.2"))
                .andExpect(status().is(404))
                .andReturn();
        ErrorResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        Assertions.assertTrue(response.getMessage().contains("do not correspond"));
        Assertions.assertTrue(response.getPhrase().contains("Did you know"));
    }

    @Test
    void getCityInfoWithWrongCoordinatesRange() throws Exception {
        MvcResult result = mockMvc.perform(get("/city-info?city=Moscow&country=Russia&latitude=155.1&longitude=39.2"))
                .andExpect(status().is(400))
                .andReturn();
        ErrorResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        Assertions.assertTrue(response.getMessage().contains("Invalid data"));
        Assertions.assertTrue(response.getPhrase().contains("Did you know"));
    }

    @Test
    void getCityInfoWithEmptyOneOfParametr() throws Exception {
        MvcResult result = mockMvc.perform(get("/city-info?city=Moscow&country=Russia&longitude=39.2"))
                .andExpect(status().is(400))
                .andReturn();
        ErrorResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        Assertions.assertTrue(response.getMessage().contains("Required request parameter"));
        Assertions.assertTrue(response.getPhrase().contains("Did you know"));
    }
}
