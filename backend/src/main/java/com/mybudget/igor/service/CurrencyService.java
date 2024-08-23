package com.mybudget.igor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

@Service
public class CurrencyService {
    private final SettingsService settingsService;

    HashMap<String,String> allCurrenciesInfo;
    LocalDateTime lastUpdate;
    HashMap<String, HashMap<String, Double>> exchangeRates = new HashMap<>();
    HashMap<String, LocalDateTime> lastUpdateTimeExchangeRate = new HashMap<>();

    public CurrencyService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }


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
                    lastUpdate = LocalDateTime.now();
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
        if(currencyFrom.equals(currencyTo)) {
            return 1.0;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // If we don't have data for currencyFrom or it's too old then fetch the data from exchange rate API
        if(!exchangeRates.containsKey(currencyFrom) || Duration.between(lastUpdateTimeExchangeRate.get(currencyFrom), LocalDateTime.now()).toHours() >= 24){
            try {
                // Create a URL object, open connection to the URL and set request method to "GET"
                URL urlObj = new URL("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" + currencyFrom + ".json");
                HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    // Create a StringBuilder to accumulate the response data, create a Scanner to read from the input stream
                    StringBuilder sb = new StringBuilder();
                    Scanner scanner = new Scanner(connection.getInputStream());

                    while (scanner.hasNext()) {
                        sb.append(scanner.nextLine());
                    }
                    // Create an ObjectMapper instance to parse JSON data
                    ObjectMapper objectMapper = new ObjectMapper();
                    // Remember the last update time for this currency
                    lastUpdateTimeExchangeRate.put(currencyFrom, LocalDateTime.now());

                    JsonNode json = objectMapper.readTree(sb.toString());
                    // Parse the date string from the JSON response and set it to the settings object
                    try {
                        settingsService.getSettings().setDate(dateFormat.parse(json.get("date").asText()));
                    } catch (ParseException ex) {
                        System.out.println("Error parsing the date: " + ex.getMessage());
                    }
                    JsonNode jsonExchangeRates = json.get(currencyFrom);
                    // Convert the JSON node into a HashMap where the key is a String and the value is a Double
                    HashMap<String, Double> rates = objectMapper.treeToValue(jsonExchangeRates, new TypeReference<HashMap<String, Double>>() {});
                    exchangeRates.put(currencyFrom, rates);
                } else {
                    System.out.println("Error is sending a GET request");
                    return 0.0;
                }
            } catch (IOException ex) {
                System.out.println("Error fetching exchange rate from '" + currencyFrom + "' to '" + currencyTo + "': " + ex.getMessage());
                return 0.0;
            }
        }

        if(exchangeRates.containsKey(currencyFrom)) {
            HashMap<String,Double> currencyFromRates = exchangeRates.get(currencyFrom);
            if(currencyFromRates.containsKey(currencyTo)) {
                return currencyFromRates.get(currencyTo);
            } else {
                System.out.println("Unknown currency " + currencyTo);
            }
        } else {
            System.out.println("Unknown currency " + currencyFrom);
        }
        System.out.println(settingsService.getSettings().getDate());

        return 0.0;

    }
}
