import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Account } from '../../models/account.interface';
import { CommonModule } from '@angular/common';
import {
  ListItem,
  ListItemComponent,
} from '../../components/list-item/list-item.component';
import { NewAccountModalComponent } from '../../components/new-account-modal/new-account-modal.component';

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

  ngOnInit(): void {
    const accounts: Account[] = [
      {
        id: 1,
        name: 'John Doe',
        currency: 'eur',
        balance: 1200.5,
      },
      {
        id: 2,
        name: 'Jane Smith',
        currency: 'usd',
        balance: 875.2,
      },
      {
        id: 3,
        name: 'Michael Brown',
        currency: 'eur',
        balance: 320.75,
      },
      {
        id: 4,
        name: 'Emily Johnson',
        currency: 'eur',
        balance: 550.1,
      },
    ];

    this.itemList = accounts.map((item) => ({
      id: item.id,
      name: item.name,
      balance: item.balance,
      currency: item.currency,
    }));
  }

  public toggleAccountModal() {
    this.showAccountModal = !this.showAccountModal;
  }
}
