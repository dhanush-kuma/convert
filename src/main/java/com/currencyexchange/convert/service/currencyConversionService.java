package com.currencyexchange.convert.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Map;
@Service
public class currencyConversionService {
    private final String API_URL;
    private final String API_KEY;

    private final RestTemplate restTemplate;

    public currencyConversionService(RestTemplate restTemplate) {
        Dotenv dotenv = Dotenv.load();
        this.API_URL = dotenv.get("API_URL");
        this.API_KEY = dotenv.get("API_KEY");
        this.restTemplate = restTemplate;
    }

    public Map<String, Double> getExchangeRates(String baseCurrency) {
        String url = API_URL +"?apikey=" + API_KEY + "&base_currency="+ baseCurrency;
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Double> rates =(Map<String, Double>) response.getBody().get("data");
            System.out.println(rates);
            return rates;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new RuntimeException("Error fetching data from API", e);
        }
    }

    public Double convertCurrency(String from, String to, Double amount) {
        Map<String, Double> rates = getExchangeRates(from);
        Double rate = rates.get(to);
        if (rate == null) {
            throw new IllegalArgumentException("Invalid currency code");
        }
        return amount * rate;
    }
}
