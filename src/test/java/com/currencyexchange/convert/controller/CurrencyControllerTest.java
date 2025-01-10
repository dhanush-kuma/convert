package com.currencyexchange.convert.controller;

import com.currencyexchange.convert.service.currencyConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Map;
public class CurrencyControllerTest {
    @Mock
    @Autowired
    private currencyConversionService currencyService;

    @InjectMocks
    @Autowired
    private currencyController currencyController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
    }

    @Test
    void testGetExchangeRates() throws Exception {
        when(currencyService.getExchangeRates("USD")).thenReturn(Map.of("EUR", 0.85, "GBP", 0.75));

        mockMvc.perform(get("/api/rates?base=USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.EUR").value(0.85))
                .andExpect(jsonPath("$.GBP").value(0.75));

        verify(currencyService, times(1)).getExchangeRates("USD");
    }

    @Test
    void testConvertCurrency() throws Exception {
        when(currencyService.convertCurrency("USD", "EUR", 100.0)).thenReturn(85.0);

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.convertedAmount").value(85.0));

        verify(currencyService, times(1)).convertCurrency("USD", "EUR", 100.0);
    }
}
