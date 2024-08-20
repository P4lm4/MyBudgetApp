import { Component, OnInit } from '@angular/core';
import {
  Transaction,
  TransactionType,
} from '../../models/transaction.interface';
import { CommonModule } from '@angular/common';
import { ListItemComponent } from '../../components/list-item/list-item.component';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, ListItemComponent],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.scss',
})
export class TransactionsComponent implements OnInit {
  public itemList: any[] = [];

  ngOnInit(): void {
    const transactions: Transaction[] = [
      {
        id: 1,
        description: 'Deposit some cash',
        type: TransactionType.INCOME,
        currency: 'eur',
        amount: 500.0,
      },
      {
        id: 2,
        description: 'Groceries',
        type: TransactionType.EXPENSE,
        currency: 'eur',
        amount: 150.75,
      },
      {
        id: 3,
        description: 'Transfer to friends account',
        type: TransactionType.EXPENSE,
        currency: 'eur',
        amount: 200.0,
      },
      {
        id: 4,
        description: 'Deposit money',
        type: TransactionType.INCOME,
        currency: 'usd',
        amount: 300.0,
      },
      {
        id: 5,
        description: 'Udemy course payment',
        type: TransactionType.EXPENSE,
        currency: 'usd',
        amount: 45.5,
      },
      {
        id: 6,
        description: 'Add money to account',
        type: TransactionType.INCOME,
        currency: 'eur',
        amount: 150.0,
      },
      {
        id: 7,
        description: 'Electricity bill payment',
        type: TransactionType.EXPENSE,
        currency: 'eur',
        amount: 60.0,
      },
    ];

    this.itemList = transactions.map((item) => ({
      id: item.id,
      name: item.description,
      balance: item.amount,
      currency: item.currency,
      type: item.type,
    }));
  }

  public options = [
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
}
