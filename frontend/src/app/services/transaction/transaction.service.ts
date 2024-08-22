import { Injectable, inject } from '@angular/core';
import { Transaction, TransactionType } from '../models/transaction.interface';
import { GlobalService } from './global.service';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  globalService: GlobalService = inject(GlobalService);

  constructor() {}

  async getAllTransactions(): Promise<Transaction[]> {
    return this.globalService.apiFetch<Transaction[]>('transaction/all');
  }

  async getTransactionsByAccountId(id: Number): Promise<Transaction[]> {
    return this.globalService.apiFetch<Transaction[]>(
      `transaction/account/${id}`
    );
  }
}
