import { inject, Injectable } from '@angular/core';
import { Account } from '../models/account.interface';
import { GlobalService } from './global.service';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  globalService: GlobalService = inject(GlobalService);

  constructor() {}

  async getAllAccount(): Promise<Account[]> {
    return this.globalService.apiFetch<Account[]>('account/all');
  }

  async calculateTotal() {
    const data = await this.globalService.apiFetch<Account[]>('account/all');
    const totalBalance = data
      .filter((acountBalance) => acountBalance.balance)
      .reduce((sum, acountBalance) => sum + acountBalance.balance, 0);

    return parseFloat(totalBalance.toFixed(2));
  }
}
