import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { TransactionsComponent } from '../../pages/transactions/transactions.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-new-transaction-modal',
  standalone: true,
  imports: [FooterComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './new-transaction-modal.component.html',
  styleUrl: './new-transaction-modal.component.scss',
})
export class NewTransactionModalComponent implements OnInit {
  @Output() closeModal = new EventEmitter();

  transactionGroup = new FormGroup({
    description: new FormControl<string>(''),
    amount: new FormControl<number>(0),
    type: new FormControl<string>(''),
    account: new FormControl<string>(''),
  });

  constructor() {}
  ngOnInit() {}

  public onCancelClick() {
    this.closeModal.emit();
  }
}
