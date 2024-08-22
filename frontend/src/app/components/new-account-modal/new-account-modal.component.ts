import {
  Component,
  effect,
  EventEmitter,
  inject,
  OnInit,
  Output,
} from '@angular/core';
import { AccountsComponent } from '../../pages/accounts/accounts.component';
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
import { AccountService } from '../../services/account/account.service';

@Component({
  selector: 'app-new-account-modal',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './new-account-modal.component.html',
  styleUrl: './new-account-modal.component.scss',
})
export class NewAccountModalComponent implements OnInit {
  @Output() closeModal = new EventEmitter();
  public currencyList: Currency[] = [];
  globalService: GlobalService = inject(GlobalService);
  accountService: AccountService = inject(AccountService);

  accountGroup = new FormGroup({
    name: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(1),
      Validators.maxLength(20),
      Validators.pattern(/\S+/),
    ]),
    balance: new FormControl<number>(0, [
      Validators.required,
      Validators.min(0),
      Validators.pattern(/^\d+(\.\d{1,2})?$/),
    ]),
    currency: new FormControl<string>('', Validators.required),
  });

  constructor() {
    effect(() => {
      this.currencyList = this.globalService.currencyList();
    });

    this.globalService.getAllCurrencies();
  }
  ngOnInit() {}

  public onCancelClick() {
    this.closeModal.emit();
  }

  public onSubmitClick() {
    if (this.accountGroup.valid) {
      const accountData = this.accountGroup.value;

      this.globalService
        .apiFetch('account/add', 'POST', accountData)
        .then((response) => {
          console.log('Account added successfully: ', response);
          this.accountService.getAllAccounts();
          this.closeModal.emit();
        })
        .catch((error) => {
          console.error('Error adding account:', error);
        });
    } else {
      console.log('Form is invalid.');
    }

    /*
    this.http
      .post('http://localhost:8080/api/account/add', accountData)
      .subscribe({
        next: (response) => {
          console.log('Account submitted successfully: ', response);
          this.closeModal.emit();
        },
        error: (error) => {
          console.error('Error submitting account', error);
        },
      });
      */
  }

  get name() {
    return this.accountGroup.get('name');
  }

  get balance() {
    return this.accountGroup.get('balance');
  }
}
