import { Component, OnInit, effect, inject } from '@angular/core';
import { Account } from '../../models/account.interface';
import { CommonModule } from '@angular/common';
import {
  ListItem,
  ListItemComponent,
} from '../../components/list-item/list-item.component';
import { NewAccountModalComponent } from '../../components/new-account-modal/new-account-modal.component';
import { AccountService } from '../../services/account/account.service';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, ListItemComponent, NewAccountModalComponent],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.scss',
})
export class AccountsComponent implements OnInit {
  public itemList: ListItem[] = [];
  public showAccountModal: boolean = false;
  public accountList: Account[] = [];
  accountService: AccountService = inject(AccountService);

  constructor() {
    effect(() => {
      this.accountList = this.accountService.accountList();
      this.updatedItemsFromAccountList();
    });
    this.accountService.getAllAccounts();
  }

  updatedItemsFromAccountList() {
    this.itemList = this.accountList.map((item) => ({
      id: item.id,
      name: item.name,
      balance: item.balance,
      currency: item.currency,
      defaultCurrency: item.defaultCurrency,
      defaultCurrencyAmount: item.defaultCurrencyAmount,
    }));
  }

  ngOnInit(): void {
    this.updatedItemsFromAccountList();
  }

  public toggleAccountModal() {
    this.showAccountModal = !this.showAccountModal;
  }
}
