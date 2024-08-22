import { Component, effect, inject, OnInit } from '@angular/core';
import { Settings } from '../../models/settings.interface';
import { Currency } from '../../models/Currency.interface';
import { GlobalService } from '../../services/global/global.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
})
export class SettingsComponent implements OnInit {
  public currencyList: Currency[] = [];
  public settings: Settings;
  public selectedCurrencyKey: String;

  globalService: GlobalService = inject(GlobalService);

  constructor() {
    effect(() => {
      this.currencyList = this.globalService.currencyList();
    });
    this.globalService.getAllCurrencies();
    this.globalService.getSettings().then((settingsData: Settings) => {
      this.settings = settingsData;
      this.selectedCurrencyKey = settingsData.currency;
    });
  }

  ngOnInit(): void {}

  public onCurrencySelectedChange(event: Event) {
    const currencyKey: string = (event.target as HTMLSelectElement).value;
    this.selectedCurrencyKey = currencyKey;
    this.globalService.setDefaultCurrency(currencyKey);
  }

  public deleteAll() {
    this.globalService.deleteAll();
  }
}
