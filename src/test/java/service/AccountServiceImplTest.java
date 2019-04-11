package service;

import exception.CustomException;
import model.Account;
import model.Transaction;
import model.User;
import model.Withdrawal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.AccountRepository;
import repository.TransactionRepository;
import repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    List<Account> accounts;
    Account account;
    List<Transaction> transactions;
    Transaction transaction;
    List<User> users;
    User user;

    @Before
    public void setUp() throws Exception {
        account = new Account(1L, 0d, 1L, "GBP");
        accounts = Arrays.asList(account, new Account(2L, 0D, 1L, "EUR"));

        transaction = new Withdrawal(1L, 1L, 10D, "GBP");
        transactions = Arrays.asList(transaction);

        users = Arrays.asList(
                new User(1L, "Username", "First", "Last"),
                new User(2L, "Username", "First", "Last"));
        user = new User(1L, "Username", "First", "Last");
    }

    @Test
    public void getAccountShouldReturnAccount() {
        when(accountRepository.getAccount(1L)).thenReturn(Collections.singletonList(account));
        List<Account> accountList = accountService.getAccount(1L);

        assertEquals(accountList.size(), 1);
        assertEquals(accountList.get(0).getId(), (Long) 1L);
    }

    @Test
    public void getAccountsShouldReturnAllAccountsForUser() {
        when(accountRepository.getAccounts(1L)).thenReturn(accounts);
        List<Account> accountList = accountService.getAccounts(1L);

        assertEquals(accountList.size(), 2);
        assertEquals(accountList.get(0).getId(), (Long) 1L);
        assertEquals(accountList.get(1).getId(), (Long) 2L);
    }

    @Test
    public void getTransactionShouldReturnTransaction() {
        when(transactionRepository.getTransaction(1L)).thenReturn(Collections.singletonList(transaction));
        List<Transaction> transactionList = accountService.getTransaction(1L);

        assertEquals(transactionList.size(), 1);
        assertEquals(transactionList.get(0).getId(), (Long) 1L);
    }

    @Test
    public void getAllTransactionsShouldReturnTransactionList() {
        when(transactionRepository.getAllTransactions()).thenReturn(transactions);
        List<Transaction> transactionList = accountService.getAllTransactions();

        assertEquals(transactionList.size(), 1);
        assertEquals(transactionList.get(0).getId(), (Long) 1L);
    }

    @Test
    public void saveTransactionShouldSaveTransaction() {
        accountService.saveTransaction(transaction);
        verify(transactionRepository, times(1)).saveTransaction(transaction);
    }

    @Test
    public void addAccountShouldSaveAccount() {
        when(userRepository.getUser(account.getOwnerId())).thenReturn(Collections.singletonList(user));
        accountService.addAccount(account);
        verify(accountRepository, times(1)).addAccount(account);
    }

    @Test(expected = CustomException.class)
    public void addAccountShouldThrowExceptionIfUserDoesNotExist() {
        when(userRepository.getUser(account.getOwnerId())).thenReturn(Collections.<User>emptyList());
        accountService.addAccount(account);
    }

    @Test
    public void withdrawMoney() {
    }

    @Test
    public void depositMoney() {
    }

    @Test
    public void transferMoney() {
    }
}