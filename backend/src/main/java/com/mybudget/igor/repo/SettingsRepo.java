package com.mybudget.igor.repo;

import com.mybudget.igor.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepo extends JpaRepository<Settings, Long> {
}
