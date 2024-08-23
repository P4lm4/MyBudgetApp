import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-confirmation-modal',
  standalone: true,
  imports: [],
  templateUrl: './confirmation-modal.component.html',
  styleUrl: './confirmation-modal.component.scss',
})
export class ConfirmationModalComponent {
  @Output() confirmAction = new EventEmitter<void>();
  @Output() cancelAction = new EventEmitter<void>();

  confirm() {
    this.confirmAction.emit();
  }

  cancel() {
    this.cancelAction.emit();
  }
}
