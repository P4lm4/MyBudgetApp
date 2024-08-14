package com.mybudget.igor;

import com.mybudget.igor.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/currency")
public class CurrencyResource {

    private final CurrencyService currencyService;

    public CurrencyResource(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/all")
    public ResponseEntity<HashMap<String, String>> getAllCurrencies() {
        HashMap<String, String> currencies = currencyService.getAllCurrencies();
        if(currencies != null) {
            return new ResponseEntity<>(currencies, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/exchange/{currencyFrom}/{currencyTo}")
    public ResponseEntity<Double> getExchangeRate(@PathVariable String currencyFrom,@PathVariable String currencyTo) {
        Double rate = currencyService.getExchangeRate(currencyFrom, currencyTo);
        if(rate != 0.0) {
            return new ResponseEntity<>(rate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
