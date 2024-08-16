package com.mybudget.igor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

@Service
public class CurrencyService {

    HashMap<String,String> allCurrenciesInfo;
    HashMap<String, Double> exchangeRateInfo;
    LocalDateTime lastUpdate = LocalDateTime.of(2024, 8, 14, 12, 0, 0);






    public HashMap<String, String> getAllCurrencies() {
        if(allCurrenciesInfo == null || Duration.between(lastUpdate, LocalDateTime.now()).toHours() >= 24) {
            try {
                URL urlObj = new URL("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.min.json");
                HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    StringBuilder sb = new StringBuilder();
                    Scanner scanner = new Scanner(connection.getInputStream());
                    while (scanner.hasNext()) {
                        sb.append(scanner.nextLine());
                    }
                    ObjectMapper objectMapper = new ObjectMapper();
                    setLastUpdate(LocalDateTime.now());
                    return allCurrenciesInfo = objectMapper.readValue(String.valueOf(sb), new TypeReference<HashMap<String, String>>(){});
                } else {
                    System.out.println("Error is sending a GET request");
                    return null;
                }
            } catch(IOException ex) {
                System.out.println("Error fetching currencies: " + ex.getMessage());
                return null;
            }
        }
        return allCurrenciesInfo;


    }

    public Double getExchangeRate (String currencyFrom, String currencyTo) {

        try {
            URL urlObj = new URL("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" + currencyFrom + ".json");
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode json = objectMapper.readTree(sb.toString());
                //System.out.println(json.get("date").asText());
                JsonNode exchangeRates = json.get(currencyFrom);
                HashMap<String, Double> rates = objectMapper.treeToValue(exchangeRates, HashMap.class);
                if(rates.containsKey(currencyTo)) {
                    return rates.get(currencyTo);
                } else {
                    System.out.println("Invalid currency " + currencyTo);
                    return 0.0;
                }
            } else {
                System.out.println("Error is sending a GET request");
                return 0.0;
            }
        } catch (IOException ex) {
            System.out.println("Error fetching exchange rate from '" + currencyFrom + "' to '" + currencyTo + "': " + ex.getMessage());
            return 0.0;
        }
    }

    public HashMap<String, String> getAllCurrenciesInfo() {
        return allCurrenciesInfo;
    }

    public void setAllCurrenciesInfo(HashMap<String, String> allCurrenciesInfo) {
        this.allCurrenciesInfo = allCurrenciesInfo;
    }

    public HashMap<String, Double> getExchangeRateInfo() {
        return exchangeRateInfo;
    }

    public void setExchangeRateInfo(HashMap<String, Double> exchangeRateInfo) {
        this.exchangeRateInfo = exchangeRateInfo;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
