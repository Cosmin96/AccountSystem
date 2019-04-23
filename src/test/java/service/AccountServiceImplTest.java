package service;

import exception.CustomException;
import model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.AccountRepository;
import repository.TransactionRepository;
import repository.UserRepository;

import javax.ws.rs.core.Response;
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

    private User user;
    private List<Account> accounts;
    private Account account;
    private Account account2;
    private Account account3;
    private Account account4;
    private List<Transaction> transactions;
    private Transaction transaction;
    private Withdrawal withdrawal;
    private Withdrawal negativeWithdrawal;
    private Withdrawal impossibleWithdrawal;
    private Withdrawal mismatchWithdrawal;
    private Withdrawal decimalWithdrawal;
    private Deposit deposit;
    private Deposit accountDeposit;
    private Deposit negativeDeposit;
    private Deposit decimalDeposit;
    private Transfer transfer;
    private Transfer accountTransfer;
    private Transfer sameTransfer;
    private Transfer negativeTransfer;
    private Transfer impossibleTransfer;
    private Transfer decimalTransfer;
    private Transfer exchangeTransfer;
    private Transfer mismatchTransfer;


    @Before
    public void setUp() throws Exception {
        user = new User(1L, "Username", "First", "Last");

        account = new Account(1L, 1000d, 1L, "GBP");
        account2 = new Account(2L, 1000d, 1L, "GBP");
        account3 = new Account(3L, 1000d, 2L, "GBP");
        account4 = new Account(4L, 1000d, 2L, "EUR");
        accounts = Arrays.asList(account, new Account(2L, 0D, 1L, "EUR"));

        transaction = new Withdrawal(1L, 1L, 10D, "GBP");
        transactions = Collections.singletonList(transaction);
        withdrawal = new Withdrawal(1L, 10D, "GBP");
        negativeWithdrawal = new Withdrawal(2L, 1L, -10D, "GBP");
        impossibleWithdrawal = new Withdrawal(2L, 1L, 10000D, "GBP");
        mismatchWithdrawal = new Withdrawal(3L, 1L, 10D, "EUR");
        decimalWithdrawal = new Withdrawal(4L, 1L, 10.002, "GBP");
        deposit = new Deposit(5L, 1L, 10.3, "GBP");
        accountDeposit = new Deposit(6L, 1L, 10.3, "EUR");
        negativeDeposit = new Deposit(7L, 1L, -10.3, "GBP");
        decimalDeposit = new Deposit(8L, 1L, 10.3123, "GBP");
        transfer = new Transfer(9L, 1L, 3L, 10D, "GBP");
        accountTransfer = new Transfer(10L, 4L, 1L, 10D, "EUR");
        sameTransfer = new Transfer(11L, 1L, 1L, 10D, "GBP");
        negativeTransfer = new Transfer(12L, 1L, 3L, -10D, "GBP");
        impossibleTransfer = new Transfer(13L, 1L, 3L, 100000D, "GBP");
        decimalTransfer = new Transfer(14L, 1L, 3L, 10.005, "GBP");
        exchangeTransfer = new Transfer(15L, 3L, 4L, 10D, "GBP");
        mismatchTransfer = new Transfer(16L, 1L, 3L, 10D, "EUR");
    }

    @Test
    public void getAccountShouldReturnAccount() {
        when(accountRepository.getAccount(1L)).thenReturn(account);
        Account account = accountService.getAccount(1L);

        assertEquals(account.getId(), (Long) 1L);
    }

    @Test
    public void getAccountsShouldReturnAllAccountsForUser() {
        when(accountRepository.getAccounts(1L)).thenReturn(accounts);
        List<Account> accountList = accountService.getAccountsForUser(1L);

        assertEquals(accountList.size(), 2);
        assertEquals(accountList.get(0).getId(), (Long) 1L);
        assertEquals(accountList.get(1).getId(), (Long) 2L);
    }

    @Test
    public void getTransactionShouldReturnTransaction() {
        when(transactionRepository.getTransaction(1L)).thenReturn(transaction);
        Transaction transaction = accountService.getTransaction(1L);

        assertEquals(transaction.getId(), (Long) 1L);
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
        when(userRepository.getUser(account.getOwnerId())).thenReturn(user);
        accountService.addAccount(account);
        verify(accountRepository, times(1)).addAccount(account);
    }

    @Test(expected = CustomException.class)
    public void addAccountShouldThrowExceptionIfUserDoesNotExist() {
        when(userRepository.getUser(account.getOwnerId())).thenReturn(user);
        accountService.addAccount(account);
    }


    @Test
    public void withdrawMoneyShouldSuccessfullyWithdrawMoney() {
        doNothing().when(accountRepository).updateAccount(any(Long.class), any(Double.class));

        accountService.withdrawMoney(account, withdrawal);

        verify(accountRepository, times(1)).updateAccount(1L, 990D);
        verify(transactionRepository, times(1)).saveTransaction(withdrawal);
    }

    @Test(expected = CustomException.class)
    public void withdrawMoneyShouldThrowExceptionIfAmountIsNegative() {
        accountService.withdrawMoney(account, negativeWithdrawal);
    }

    @Test(expected = CustomException.class)
    public void withdrawMoneyShouldThrowExceptionIfNotEnoughFunds() {
        accountService.withdrawMoney(account, impossibleWithdrawal);
    }

    @Test(expected = CustomException.class)
    public void withdrawMoneyShouldThrowExceptionIfCurrencyMismatch() {
        accountService.withdrawMoney(account, mismatchWithdrawal);
    }

    @Test(expected = CustomException.class)
    public void withdrawMoneyShouldThrowExceptionIfDecimalsAreWrong() {
        accountService.withdrawMoney(account, decimalWithdrawal);
    }

    @Test
    public void depositMoneyShouldSuccessfullyDepositMoney() {
        doNothing().when(accountRepository).updateAccount(any(Long.class), any(Double.class));

        accountService.depositMoney(account, deposit);

        verify(accountRepository, times(1)).updateAccount(1L, 1010.3D);
        verify(transactionRepository, times(1)).saveTransaction(deposit);
    }

    @Test
    public void depositMoneyShouldCreateAccountInTransactionCurrency() {
        doNothing().when(accountRepository).updateAccount(any(Long.class), any(Double.class));
        when(userRepository.getUser(any(Long.class))).thenReturn(user);

        accountService.depositMoney(account, accountDeposit);

        verify(accountRepository, times(1)).addAccount(any(Account.class));
    }

    @Test(expected = CustomException.class)
    public void depositMoneyShouldThrowExceptionIfAmountIsNegative() {
        accountService.depositMoney(account, negativeDeposit);
    }

    @Test(expected = CustomException.class)
    public void depositMoneyShouldThrowExceptionIfDecimalsAreWrong() {
        accountService.depositMoney(account, decimalDeposit);
    }

    @Test
    public void transferMoneyShouldSuccessfullyTransferMoney() {
        doNothing().when(accountRepository).updateAccount(any(Long.class), any(Double.class));

        accountService.transferMoney(account, account3, transfer);

        verify(accountRepository, times(1)).updateAccount(1L, 990D);
        verify(accountRepository, times(1)).updateAccount(3L, 1010D);
        verify(transactionRepository, times(1)).saveTransaction(transfer);
    }

    @Test
    public void transferMoneyShouldCreateAccountInTransactionCurrency() {
        doNothing().when(accountRepository).updateAccount(any(Long.class), any(Double.class));
        when(userRepository.getUser(any(Long.class))).thenReturn(user);

        accountService.transferMoney(account4, account, accountTransfer);

        verify(accountRepository, times(1)).addAccount(any(Account.class));
    }

    @Test(expected = CustomException.class)
    public void transferMoneyShouldThrowExceptionIfSameAccount() {
        accountService.transferMoney(account, account, sameTransfer);
    }

    @Test(expected = CustomException.class)
    public void transferMoneyShouldThrowExceptionIfAmountIsNegative() {
        accountService.transferMoney(account, account2, negativeTransfer);
    }

    @Test(expected = CustomException.class)
    public void transferMoneyShouldThrowExceptionIfInsufficientFunds() {
        accountService.transferMoney(account, account2, impossibleTransfer);
    }

    @Test(expected = CustomException.class)
    public void transferMoneyShouldThrowExceptionIfDecimalsAreWrong() {
        accountService.transferMoney(account, account3, decimalTransfer);
    }

    @Test(expected = CustomException.class)
    public void transferMoneyShouldThrowExceptionIfExchangeCurrency() {
        accountService.transferMoney(account3, account4, exchangeTransfer);
    }

    @Test(expected = CustomException.class)
    public void transferMoneyShouldThrowExceptionIfCurrencyMismatch() {
        accountService.transferMoney(account, account3, mismatchTransfer);
    }
}