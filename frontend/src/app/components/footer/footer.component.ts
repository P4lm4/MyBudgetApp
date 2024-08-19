import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Account } from '../../models/account.interface';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss',
})
export class FooterComponent implements OnInit {
  public accounts: Account[] = [
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

  public totalBalance: number = 0;

  ngOnInit(): void {
    this.calculateTotal();
  }

  private calculateTotal(): void {
    this.totalBalance = this.accounts
      .filter((acountBalance) => acountBalance.balance)
      .reduce((sum, acountBalance) => sum + acountBalance.balance, 0);

    this.totalBalance = parseFloat(this.totalBalance.toFixed(2));
  }
}
