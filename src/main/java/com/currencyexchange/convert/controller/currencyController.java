package com.currencyexchange.convert.controller;

import com.currencyexchange.convert.service.currencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class currencyController {


    private final currencyConversionService currencyService;

    @Autowired
    public currencyController(currencyConversionService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/rates")
    public ResponseEntity<Map<String, Double>> getExchangeRates(@RequestParam(value = "base", defaultValue = "USD") String base) {
        Map<String, Double> rates = currencyService.getExchangeRates(base);
        return ResponseEntity.ok(rates);
    }

    @PostMapping("/convert")
    public ResponseEntity<Map<String, Object>> convertCurrency(@RequestBody Map<String, Object> requestBody) {
        String from = (String) requestBody.get("from");
        String to = (String) requestBody.get("to");
        Double amount = (Double) requestBody.get("amount");

        Double convertedAmount = currencyService.convertCurrency(from, to, amount);

        Map<String, Object> response = Map.of(
                "from", from,
                "to", to,
                "amount", amount,
                "convertedAmount", convertedAmount
        );
        return ResponseEntity.ok(response);
    }
}
