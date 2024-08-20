import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AccountsComponent } from '../../pages/accounts/accounts.component';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';

@Component({
  selector: 'app-new-account-modal',
  standalone: true,
  imports: [AccountsComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './new-account-modal.component.html',
  styleUrl: './new-account-modal.component.scss',
})
export class NewAccountModalComponent implements OnInit {
  @Output() closeModal = new EventEmitter();

  accountGroup = new FormGroup({
    name: new FormControl<string>(''),
    balance: new FormControl<number>(0),
    currency: new FormControl<string>(''),
  });

  constructor() {}
  ngOnInit() {}

  public onCancelClick() {
    this.closeModal.emit();
  }
}
