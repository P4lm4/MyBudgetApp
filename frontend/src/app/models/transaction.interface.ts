export interface Transaction {
  id: number;
  description: string;
  type: TransactionType;
  currency: string;
  amount: number;
  acountId: number;
  accountName: string;
  defaultCurrency: string;
  defaultCurrencyAmount: number;
}

export enum TransactionType {
  INCOME = 'INCOME',
  EXPENSE = 'EXPENSE',
}
