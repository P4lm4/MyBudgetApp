import { CommonModule } from '@angular/common';
import { Component, effect, inject, OnInit } from '@angular/core';
import { Account } from '../../models/account.interface';
import { NewTransactionModalComponent } from '../new-transaction-modal/new-transaction-modal.component';
import { AccountService } from '../../services/account/account.service';
import { GlobalService } from '../../services/global/global.service';
import { Settings } from '../../models/settings.interface';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule, NewTransactionModalComponent],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss',
})
export class FooterComponent implements OnInit {
  public showTransactionModal: boolean = false;
  public totalBalance: number = 0;
  public settings: Settings;
  public selectedCurrencyKey: string;
  accountService: AccountService = inject(AccountService);
  globalService: GlobalService = inject(GlobalService);

  constructor() {
    effect(() => {
      this.totalBalance = this.accountService.totalBalance();
      this.globalService.getSettings().then((settingsData: Settings) => {
        this.settings = settingsData;
        this.selectedCurrencyKey = settingsData.currency;
      });
    });
  }

  ngOnInit() {}

  public toggleTransactionModal() {
    this.showTransactionModal = !this.showTransactionModal;
  }
}
