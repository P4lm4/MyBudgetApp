import {
  Component,
  effect,
  EventEmitter,
  inject,
  OnInit,
  Output,
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { GlobalService } from '../../services/global/global.service';
import { Currency } from '../../models/Currency.interface';
import { Account } from '../../models/account.interface';
import { AccountService } from '../../services/account/account.service';
import { TransactionService } from '../../services/transaction/transaction.service';

@Component({
  selector: 'app-new-transaction-modal',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './new-transaction-modal.component.html',
  styleUrl: './new-transaction-modal.component.scss',
})
export class NewTransactionModalComponent implements OnInit {
  @Output() closeModal = new EventEmitter();
  public currencyList: Currency[] = [];
  public accountList: Account[] = [];
  type = {
    INCOME: 'INCOME',
    EXPENSE: 'EXPENSE',
  };
  globalService: GlobalService = inject(GlobalService);
  accountService: AccountService = inject(AccountService);
  transactionService: TransactionService = inject(TransactionService);

  transactionGroup = new FormGroup({
    description: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(1),
      Validators.maxLength(20),
      Validators.pattern(/\S+/),
    ]),
    amount: new FormControl<number>(0, Validators.required),
    type: new FormControl<string>(this.type.INCOME, Validators.required),
    currency: new FormControl<string>('', Validators.required),
    account: new FormControl<string>('', Validators.required),
  });

  constructor() {
    effect(() => {
      this.accountList = this.accountService.accountList();
      this.currencyList = this.globalService.currencyList();
    });
    this.accountService.getAllAccounts();
    this.globalService.getAllCurrencies();
  }

  ngOnInit() {}

  public onCancelClick() {
    this.closeModal.emit();
  }

  public onSubmitClick() {
    if (this.transactionGroup.valid) {
      const transactionData = this.transactionGroup.value;
      const accountId = this.transactionGroup.value.account;
      this.globalService
        .apiFetch(`transaction/add/${accountId}`, 'POST', transactionData)
        .then((response) => {
          console.log('Transaction added successfully: ', response);
          this.accountService.getAllAccounts();
          this.transactionService.getAllTransactions();
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
