import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FooterComponent } from '../footer/footer.component';
import { CommonModule } from '@angular/common';
import { GlobalService } from '../../services/global.service';
import { Currency } from '../../models/Currency.interface';
import { Account } from '../../models/account.interface';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-new-transaction-modal',
  standalone: true,
  imports: [FooterComponent, FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './new-transaction-modal.component.html',
  styleUrl: './new-transaction-modal.component.scss',
})
export class NewTransactionModalComponent implements OnInit {
  @Output() closeModal = new EventEmitter();
  public currencyList: Currency[] = [];
  public accountList: Account[] = [];
  globalService: GlobalService = inject(GlobalService);
  accountService: AccountService = inject(AccountService);

  transactionGroup = new FormGroup({
    description: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(1),
      Validators.maxLength(20),
      Validators.pattern(/\S+/),
    ]),
    amount: new FormControl<number>(0, Validators.required),
    type: new FormControl<string>('', Validators.required),
    account: new FormControl<string>('', Validators.required),
  });

  constructor() {
    this.globalService.getAllCurrencies().then((currencyList: Currency[]) => {
      this.currencyList = currencyList;
      this.accountService.getAllAccount().then((accountList: Account[]) => {
        this.accountList = accountList;
      });
    });
  }
  ngOnInit() {}

  public onCancelClick() {
    this.closeModal.emit();
  }

  public onSubmitClick() {
    if (this.transactionGroup.valid) {
      const accountData = this.transactionGroup.value;

      this.globalService
        .apiFetch('account/add', 'POST', accountData)
        .then((response) => {
          console.log('Transaction added successfully: ', response);
          this.closeModal.emit();
        })
        .catch((error) => {
          console.error('Error adding transaction:', error);
        });
    } else {
      console.log('Form is invalid.');
    }
  }

  get description() {
    return this.transactionGroup.get('description');
  }
  get amount() {
    return this.transactionGroup.get('amount');
  }
}
