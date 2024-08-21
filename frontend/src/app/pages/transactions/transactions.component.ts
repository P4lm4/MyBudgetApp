import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import {
  Transaction,
  TransactionType,
} from '../../models/transaction.interface';
import { CommonModule } from '@angular/common';
import { ListItemComponent } from '../../components/list-item/list-item.component';
import { TransactionService } from '../../services/transaction.service';
import { AccountService } from '../../services/account.service';
import { Account } from '../../models/account.interface';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, ListItemComponent],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.scss',
})
export class TransactionsComponent implements OnInit {
  public itemList: any[] = [];
  public transactionList: Transaction[] = [];
  public accountList: Account[] = [];
  transactionService: TransactionService = inject(TransactionService);
  accountService: AccountService = inject(AccountService);

  constructor() {
    //this.transactionList = this.transactionService.getAllTransactions();
    this.transactionService
      .getAllTransactions()
      .then((transactionList: Transaction[]) => {
        this.setTransactionList(transactionList);
      });
    this.accountService.getAllAccount().then((accountList: Account[]) => {
      this.accountList = accountList;
    });
  }

  updatedItemsFromTransactionList() {
    this.itemList = this.transactionList.map((item) => ({
      id: item.id,
      name: item.description,
      balance: item.amount,
      currency: item.currency,
      type: item.type,
    }));
  }

  ngOnInit(): void {
    this.updatedItemsFromTransactionList();
  }

  public onAccountSelectedChange(event: Event) {
    const accountId: number = parseInt(
      (event.target as HTMLSelectElement).value
    );
    if (accountId >= 0) {
      this.transactionService
        .getTransactionsByAccountId(accountId)
        .then((transactionList: Transaction[]) => {
          this.setTransactionList(transactionList);
        });
    } else {
      this.transactionService
        .getAllTransactions()
        .then((transactionList: Transaction[]) => {
          this.setTransactionList(transactionList);
        });
    }
  }

  setTransactionList(list: Transaction[]) {
    this.transactionList = list;
    this.updatedItemsFromTransactionList();
  }
}
