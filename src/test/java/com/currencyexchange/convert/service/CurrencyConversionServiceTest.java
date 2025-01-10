package com.currencyexchange.convert.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Map;

public class CurrencyConversionServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private currencyConversionService currencyService;

    @BeforeEach
    void setUp() {
        currencyService = new currencyConversionService(restTemplate);
    }

    @Test
    void testGetExchangeRates() {
        // Mock data
        Map<String, Double> mockRates = Map.of("EUR", 0.85, "GBP", 0.75);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(Map.of("rates", mockRates));

        Map<String, Double> rates = currencyService.getExchangeRates("USD");
        assertEquals(0.85, rates.get("EUR"));
    }

    @Test
    void testConvertCurrency() {
        // Mock data
        Map<String, Double> mockRates = Map.of("EUR", 0.85);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(Map.of("rates", mockRates));

        Double convertedAmount = currencyService.convertCurrency("USD", "EUR", 100.0);
        assertEquals(85.0, convertedAmount);
    }

    @Test
    void testConvertCurrency_InvalidCurrency() {
        Map<String, Double> mockRates = Map.of("EUR", 0.85);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(Map.of("rates", mockRates));

        assertThrows(IllegalArgumentException.class, () -> currencyService.convertCurrency("USD", "INVALID", 100.0));
    }
}
