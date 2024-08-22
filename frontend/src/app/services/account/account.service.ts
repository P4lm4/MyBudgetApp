import { inject, Injectable, signal, WritableSignal } from '@angular/core';
import { Account } from '../../models/account.interface';
import { GlobalService } from '../global/global.service';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  public accountList: WritableSignal<Account[]> = signal([]);
  public totalBalance: WritableSignal<number> = signal(0);
  globalService: GlobalService = inject(GlobalService);

  constructor() {}

  async getAllAccounts(): Promise<void> {
    this.globalService
      .apiFetch<Account[]>('account/all')
      .then((value: Account[]) => {
        this.accountList.set(value);
        this.calculateTotal();
      });
  }

  private calculateTotal() {
    const data = this.accountList();
    const totalBalance = data
      .filter((acountBalance) => acountBalance.defaultCurrencyAmount)
      .reduce(
        (sum, acountBalance) => sum + acountBalance.defaultCurrencyAmount,
        0
      );

    this.totalBalance.set(totalBalance);
  }
}
