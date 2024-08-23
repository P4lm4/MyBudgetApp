import { Injectable, signal, inject, WritableSignal } from '@angular/core';
import { Transaction } from '../../models/transaction.interface';
import { GlobalService } from '../global/global.service';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  public transactionList: WritableSignal<Transaction[]> = signal([]);
  globalService: GlobalService = inject(GlobalService);

  constructor() {}

  async getAllTransactions(): Promise<void> {
    this.globalService
      .apiFetch<Transaction[]>('transaction/all')
      .then((transactionList: Transaction[]) => {
        this.transactionList.set(transactionList);
      });
  }

  async getTransactionsByAccountId(id: Number): Promise<void> {
    this.globalService
      .apiFetch<Transaction[]>(`transaction/account/${id}`)
      .then((transactionList: Transaction[]) => {
        this.transactionList.set(transactionList);
      });
  }
}
