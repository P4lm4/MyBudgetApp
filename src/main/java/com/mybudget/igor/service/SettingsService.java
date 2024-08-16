package com.mybudget.igor.service;

import com.mybudget.igor.model.Settings;
import com.mybudget.igor.repo.SettingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SettingsService {
    private final SettingsRepo settingsRepo;
    private final CurrencyService currencyService;

    public SettingsService(SettingsRepo settingsRepo,@Lazy CurrencyService currencyService) {
        this.settingsRepo = settingsRepo;
        this.currencyService = currencyService;
    }

    public Settings getSettings() {
        Settings s = settingsRepo.findById(1L).orElse(null);
        if(s != null) {

            return s;
        }
        s = new Settings();

        s.setCurrency("eur");
        s.setDate(new Date());

        return settingsRepo.save(s);
    }

    public void setCurrency(String c) {
        Settings s = getSettings();
        if(!currencyService.getAllCurrencies().containsKey(c)) {
            throw  new IllegalArgumentException("Currency " + c + " does not exist.");
        }
        s.setCurrency(c);

        settingsRepo.save(s);
    }


}
