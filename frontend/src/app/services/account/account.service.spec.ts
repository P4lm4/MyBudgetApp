import { TestBed } from '@angular/core/testing';

import { AccountService } from './account.service';
import { Account } from '../../models/account.interface';
import { GlobalService } from '../global/global.service';

describe('AccountService', () => {
  let service: AccountService;
  let globalService: jasmine.SpyObj<GlobalService>;

  beforeEach(() => {
    const globalServiceSpy = jasmine.createSpyObj('GlobalService', [
      'apiFetch',
    ]);

    TestBed.configureTestingModule({
      providers: [
        AccountService,
        { provide: GlobalService, useValue: globalServiceSpy },
      ],
    });

    service = TestBed.inject(AccountService);
    globalService = TestBed.inject(
      GlobalService
    ) as jasmine.SpyObj<GlobalService>;
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all accounts and calculate total balance', async () => {
    const mockAccounts: Account[] = [
      {
        id: 1,
        name: 'Account 1',
        currency: 'eur',
        balance: 500,
        defaultCurrency: 'eur',
        defaultCurrencyAmount: 100,
      },
      {
        id: 2,
        name: 'Account 2',
        currency: 'eur',
        balance: 500,
        defaultCurrency: 'eur',
        defaultCurrencyAmount: 200,
      },
    ];

    globalService.apiFetch.and.returnValue(Promise.resolve(mockAccounts));

    await service.getAllAccounts();

    expect(service.accountList()).toEqual(mockAccounts);
    expect(service.totalBalance()).toBe(300); // 100 + 200
  });

  it('should handle empty account list', async () => {
    globalService.apiFetch.and.returnValue(Promise.resolve([]));

    await service.getAllAccounts();

    expect(service.accountList()).toEqual([]);
    expect(service.totalBalance()).toBe(0);
  });

  it('should handle accounts with no defaultCurrencyAmount', async () => {
    const mockAccounts: Account[] = [
      {
        id: 1,
        name: 'Account 1',
        currency: 'eur',
        balance: 500,
        defaultCurrency: 'eur',
        defaultCurrencyAmount: 100,
      },
      {
        id: 2,
        name: 'Account 2',
        currency: 'eur',
        balance: 500,
        defaultCurrency: 'eur',
        defaultCurrencyAmount: null as any,
      },
    ];

    globalService.apiFetch.and.returnValue(Promise.resolve(mockAccounts));

    await service.getAllAccounts();

    expect(service.accountList()).toEqual(mockAccounts);
    expect(service.totalBalance()).toBe(100); // only 100 should be counted
  });
});
