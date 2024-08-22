import {
  ChangeDetectorRef,
  Component,
  effect,
  inject,
  OnInit,
} from '@angular/core';
import {
  Transaction,
  TransactionType,
} from '../../models/transaction.interface';
import { CommonModule } from '@angular/common';
import { ListItemComponent } from '../../components/list-item/list-item.component';
import { TransactionService } from '../../services/transaction/transaction.service';
import { Account } from '../../models/account.interface';
import { AccountService } from '../../services/account/account.service';

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
    effect(() => {
      this.transactionList = this.transactionService.transactionList();
      this.updatedItemsFromTransactionList();
      this.accountList = this.accountService.accountList();
    });
    this.accountService.getAllAccounts();
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
    this.transactionService.getAllTransactions();
  }

  public onAccountSelectedChange(event: Event) {
    const accountId: number = parseInt(
      (event.target as HTMLSelectElement).value
    );
    if (accountId >= 0) {
      this.transactionService.getTransactionsByAccountId(accountId);
    } else {
      this.transactionService.getAllTransactions();
    }
  }

  setTransactionList(list: Transaction[]) {
    this.transactionList = list;
    this.updatedItemsFromTransactionList();
  }
}
