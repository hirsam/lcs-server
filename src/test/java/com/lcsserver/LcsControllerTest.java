package com.lcsserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcsserver.controller.LcsController;
import com.lcsserver.dto.LcsErrorResponse;
import com.lcsserver.dto.LcsRequest;
import com.lcsserver.dto.ValueDto;
import com.lcsserver.service.LcsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static com.lcsserver.util.ErrorMessages.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class LcsControllerTest {

    @Mock
    private LcsService lcsService;

    @InjectMocks
    private LcsController lcsController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String LCS_ENDPOINT = "/lcs";


    @Test
    public void testValidRequest() throws Exception {
        LcsRequest request = new LcsRequest();
        request.setValues(Arrays.asList(new ValueDto("comcast"), new ValueDto("comcastic"), new ValueDto("broadcaster")));


        mockMvc.perform(MockMvcRequestBuilders.post(LCS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lcs", hasSize(1)))
                .andExpect(jsonPath("$.lcs[0].value", is("cast")));
    }

    @Test
    public void testEmptyRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lcs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getErrorResponseAsString(REQUEST_BODY_EMPTY_MESSAGE)));
    }

    @Test
    public void testEmptyValues() throws Exception {
        LcsRequest request = new LcsRequest();
        request.setValues(Arrays.asList(new ValueDto("")));

        mockMvc.perform(MockMvcRequestBuilders.post(LCS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getErrorResponseAsString(EMPTY_VALUES_MESSAGE)));
    }

    @Test
    public void testNonUniqueValues() throws Exception {
        LcsRequest request = new LcsRequest();
        request.setValues(Arrays.asList(new ValueDto("comcast"), new ValueDto("comcast")));

        mockMvc.perform(MockMvcRequestBuilders.post(LCS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getErrorResponseAsString(VALUES_MUST_BE_A_SET_MESSAGE)));
    }

    private String getErrorResponseAsString(String message) {
        try {
            return objectMapper.writeValueAsString(new LcsErrorResponse(message));
        }
        catch (Exception e) {
            return "";
        }
    }
}