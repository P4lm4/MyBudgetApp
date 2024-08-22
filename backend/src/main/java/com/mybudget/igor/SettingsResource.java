package com.mybudget.igor;

import com.mybudget.igor.model.Settings;
import com.mybudget.igor.repo.SettingsRepo;
import com.mybudget.igor.service.AccountService;
import com.mybudget.igor.service.SettingsService;
import com.mybudget.igor.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingsResource {
    private final SettingsService settingsService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public SettingsResource(SettingsService settingsService, AccountService accountService, TransactionService transactionService) {
        this.settingsService = settingsService;
        this.accountService = accountService;
        this.transactionService = transactionService;
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

    @GetMapping("/deleteAll")
    public ResponseEntity<Boolean> deleteAll() {
        transactionService.deleteAll();
        settingsService.deleteAll();
        accountService.deleteAll();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
