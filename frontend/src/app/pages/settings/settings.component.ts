import { Component, inject } from '@angular/core';
import { Settings } from '../../models/settings.interface';
import { Currency } from '../../models/Currency.interface';
import { GlobalService } from '../../services/global.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
})
export class SettingsComponent {
  public currencyList: Currency[] = [];

  globalService: GlobalService = inject(GlobalService);

  constructor() {
    this.globalService.getAllCurrencies().then((currencyList: Currency[]) => {
      this.currencyList = currencyList;
    });
  }
}
