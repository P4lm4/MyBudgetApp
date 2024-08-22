import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

export interface ListItem {
  id: number;
  name: string;
  balance: number;
  currency: string;
  type?: string;
  accountId?: number;
  accountName?: string;
  defaultCurrency: string;
  defaultCurrencyAmount: number;
}

@Component({
  selector: 'app-list-item',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './list-item.component.html',
  styleUrl: './list-item.component.scss',
})
export class ListItemComponent {
  @Input() item: ListItem;
}
