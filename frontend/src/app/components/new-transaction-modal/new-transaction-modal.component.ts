import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FooterComponent } from '../footer/footer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-new-transaction-modal',
  standalone: true,
  imports: [FooterComponent, FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './new-transaction-modal.component.html',
  styleUrl: './new-transaction-modal.component.scss',
})
export class NewTransactionModalComponent implements OnInit {
  @Output() closeModal = new EventEmitter();

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

  constructor() {}
  ngOnInit() {}

  public onCancelClick() {
    this.closeModal.emit();
  }

  get description() {
    return this.transactionGroup.get('description');
  }
  get amount() {
    return this.transactionGroup.get('amount');
  }
}
