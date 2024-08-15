package com.mybudget.igor.service;

import com.mybudget.igor.model.Settings;
import com.mybudget.igor.repo.SettingsRepo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SettingsService {
    private final SettingsRepo settingsRepo;

    public SettingsService(SettingsRepo settingsRepo) {
        this.settingsRepo = settingsRepo;
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
}
