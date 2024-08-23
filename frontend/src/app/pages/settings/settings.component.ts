import { Component, effect, inject, OnInit } from '@angular/core';
import { Settings } from '../../models/settings.interface';
import { Currency } from '../../models/Currency.interface';
import { GlobalService } from '../../services/global/global.service';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../services/account/account.service';
import { ConfirmationModalComponent } from '../../components/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, ConfirmationModalComponent],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
})
export class SettingsComponent implements OnInit {
  public currencyList: Currency[] = [];
  public settings: Settings;
  public selectedCurrencyKey: String;
  showModal = false;

  globalService: GlobalService = inject(GlobalService);
  accountService: AccountService = inject(AccountService);

  constructor() {
    effect(() => {
      this.currencyList = this.globalService.currencyList();
    });
    this.globalService.getAllCurrencies();
    this.accountService.getAllAccounts();
    this.globalService.getSettings().then((settingsData: Settings) => {
      this.settings = settingsData;
      this.selectedCurrencyKey = settingsData.currency;
    });
  }

  ngOnInit(): void {}

  public async onCurrencySelectedChange(event: Event) {
    const currencyKey: string = (event.target as HTMLSelectElement).value;
    this.selectedCurrencyKey = currencyKey;
    await this.globalService.setDefaultCurrency(currencyKey);
    this.accountService.getAllAccounts();
  }

  public deleteAll() {
    this.globalService.deleteAll();
    this.showModal = false;
  }
}
