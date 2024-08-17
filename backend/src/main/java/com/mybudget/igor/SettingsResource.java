package com.mybudget.igor;

import com.mybudget.igor.model.Settings;
import com.mybudget.igor.repo.SettingsRepo;
import com.mybudget.igor.service.SettingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingsResource {
    private final SettingsService settingsService;

    public SettingsResource(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping("/get")
    public ResponseEntity<Settings> getSettings() {
        Settings settings = settingsService.getSettings();
        return new ResponseEntity<>(settings, HttpStatus.OK);
    }

    @PostMapping("/currency/{currency}")
    public ResponseEntity<String> setCurrency(@PathVariable String currency) {
        settingsService.setCurrency(currency);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
