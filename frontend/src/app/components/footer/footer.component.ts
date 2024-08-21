import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Account } from '../../models/account.interface';
import { NewTransactionModalComponent } from '../new-transaction-modal/new-transaction-modal.component';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule, NewTransactionModalComponent],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss',
})
export class FooterComponent implements OnInit {
  public showTransactionModal: boolean = false;
  public totalBalance: number = 0;
  accountService: AccountService = inject(AccountService);

  constructor() {}

  ngOnInit() {
    this.accountService.calculateTotal().then((totalBalance: number) => {
      this.totalBalance = totalBalance;
    });
  }

  public toggleTransactionModal() {
    this.showTransactionModal = !this.showTransactionModal;
  }
}
