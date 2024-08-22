package com.mybudget.igor;
import com.mybudget.igor.model.Account;
import com.mybudget.igor.model.Transaction;
import com.mybudget.igor.model.TransactionType;
import com.mybudget.igor.repo.AccountRepo;
import com.mybudget.igor.repo.TransactionRepo;
import com.mybudget.igor.service.CurrencyService;
import com.mybudget.igor.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private CurrencyService currencyService;
    @Mock
    private TransactionRepo transactionRepo;
    @InjectMocks
    private TransactionService transactionService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId(1L);
        account.setName("New user");
        account.setBalance(100.0);
        account.setCurrency("eur");
    }

    @Test
    void testIncome() {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(20.0);
        transaction.setDescription("Income");
        transaction.setCurrency("eur");
        transaction.setType(TransactionType.INCOME);
        HashMap<String, String> mockCurrencies = new HashMap<String, String>();
        mockCurrencies.put("eur", "Euro");
        when(currencyService.getAllCurrencies()).thenReturn(mockCurrencies);

        transactionService.addTransaction(transaction);

        assertEquals(120.0, account.getBalance());
    }

    @Test
    void testInsufficientFunds() {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(200.0);
        transaction.setDescription("Expense");
        transaction.setCurrency("eur");
        transaction.setType(TransactionType.EXPENSE);
        HashMap<String, String> mockCurrencies = new HashMap<String, String>();
        mockCurrencies.put("eur", "Euro");
        when(currencyService.getAllCurrencies()).thenReturn(mockCurrencies);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.addTransaction(transaction);
        });

        assertEquals("You don't have enough in your account, current balance: " + account.getBalance(), exception.getMessage());
    }

    @Test
    void testIncomeConversion() {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(50.0);
        transaction.setDescription("Income");
        transaction.setCurrency("bam");
        transaction.setType(TransactionType.INCOME);
        HashMap<String, String> mockCurrencies = new HashMap<String, String>();
        mockCurrencies.put("bam", "Marka");
        mockCurrencies.put("eur", "Euro");
        when(currencyService.getAllCurrencies()).thenReturn(mockCurrencies);
        when(currencyService.getExchangeRate("bam","eur")).thenReturn(0.5);

        transactionService.addTransaction(transaction);

        assertEquals(125.0, account.getBalance());
    }
}
