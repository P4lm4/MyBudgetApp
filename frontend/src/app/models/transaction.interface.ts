export interface Transaction {
  description: string;
  type: TransactionType;
  currency: string;
  amount: number;
}

export enum TransactionType {
  INCOME = 'INCOME',
  EXPENSE = 'EXPENSE',
}
