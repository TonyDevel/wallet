package com.wallet.service.impl;

import com.wallet.ConversionTestUtil;
import com.wallet.exception.AccountNotFoundException;
import com.wallet.exception.InsufficientFundsToWithdrawException;
import com.wallet.model.converter.AccountToDTOConverter;
import com.wallet.model.converter.RegisterAccountRequestToEntityConverter;
import com.wallet.model.dto.account.AccountDTO;
import com.wallet.model.dto.account.RegisterAccountRequest;
import com.wallet.model.entity.Account;
import com.wallet.model.entity.Transaction;
import com.wallet.model.entity.TransactionType;
import com.wallet.repository.AccountRepository;
import com.wallet.util.MoneyUtils;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    private final Integer accountId = 255;
    private final Integer playerId = 494;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Spy
    private ConversionService conversionService = ConversionTestUtil.initConverters(
            new RegisterAccountRequestToEntityConverter(),
            new AccountToDTOConverter()
    );


    @Test
    public void existsTest() {
        when(accountRepository.existsById(accountId)).thenReturn(true);

        boolean result = accountService.exists(accountId);

        assertTrue(result);
        verify(accountRepository, times(1)).existsById(accountId);
    }

    @Test
    public void sufficientFundsToWithdrawTest() {
        BigDecimal amountToWithdraw = BigDecimal.TEN;
        when(accountRepository.sufficientFundsToWithdraw(accountId, amountToWithdraw)).thenReturn(false);

        boolean result = accountService.sufficientFundsToWithdraw(accountId, amountToWithdraw);

        assertFalse(result);
        verify(accountRepository, times(1)).sufficientFundsToWithdraw(accountId, amountToWithdraw);
    }

    @Test
    public void registerTest() {
        RegisterAccountRequest request = new RegisterAccountRequest();
        request.setPlayerId(playerId);
        BigDecimal expectedInitialAmount = MoneyUtils.setMoneyScale(BigDecimal.ZERO);
        when(accountRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));


        AccountDTO account = accountService.register(request);

        assertNotNull(account);
        assertEquals(playerId, account.getPlayerId());
        assertEquals(expectedInitialAmount, account.getBalance());
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    public void getPlayerAccountTestPositive() {
        BigDecimal balance = MoneyUtils.setMoneyScale(BigDecimal.valueOf(15.155));
        Account account = new Account();
        account.setId(accountId);
        account.setPlayerId(playerId);
        account.setAmount(balance);
        when(accountRepository.findByPlayerId(playerId)).thenReturn(account);

        AccountDTO playerAccount = accountService.getPlayerAccount(playerId);

        assertNotNull(playerAccount);
        assertEquals(accountId, playerAccount.getId());
        assertEquals(playerId, playerAccount.getPlayerId());
        assertEquals(balance, playerAccount.getBalance());

        verify(accountRepository, times(1)).findByPlayerId(playerId);
    }

    @Test(expected = AccountNotFoundException.class)
    public void getPlayerAccountTestNegative() {
        when(accountRepository.findByPlayerId(playerId)).thenReturn(null);

        accountService.getPlayerAccount(playerId);

        verify(accountRepository, times(1)).findByPlayerId(playerId);
    }

    @Test
    public void applyCreditTransactionTest() {
        Transaction transaction = buildTransaction(TransactionType.CREDIT);

        accountService.applyTransaction(transaction);

        verify(accountRepository, times(1)).addFunds(accountId, transaction.getAmount());
        verify(accountRepository, times(0)).withdrawFunds(eq(accountId), any());
    }

    @Test
    public void applyDebitTransactionTest() {
        Transaction transaction = buildTransaction(TransactionType.DEBIT);
        when(accountRepository.sufficientFundsToWithdraw(accountId, transaction.getAmount())).thenReturn(true);
        when(accountRepository.isNegativeBalance(accountId)).thenReturn(false);

        accountService.applyTransaction(transaction);

        verify(accountRepository, times(1)).withdrawFunds(accountId, transaction.getAmount());
        verify(accountRepository, times(1)).isNegativeBalance(accountId);
        verify(accountRepository, times(0)).addFunds(eq(accountId), any());
    }

    @Test(expected = InsufficientFundsToWithdrawException.class)
    public void applyDebitTransactionWithNegativeBalanceBeforeOperationTest() {
        Transaction transaction = buildTransaction(TransactionType.DEBIT);
        when(accountRepository.sufficientFundsToWithdraw(accountId, transaction.getAmount())).thenReturn(false);

        accountService.applyTransaction(transaction);

        verify(accountRepository, times(0)).withdrawFunds(accountId, transaction.getAmount());
        verify(accountRepository, times(0)).isNegativeBalance(accountId);
        verify(accountRepository, times(0)).addFunds(eq(accountId), any());
    }

    @Test(expected = InsufficientFundsToWithdrawException.class)
    public void applyDebitTransactionWithNegativeResultBalanceTest() {
        Transaction transaction = buildTransaction(TransactionType.DEBIT);
        when(accountRepository.sufficientFundsToWithdraw(accountId, transaction.getAmount())).thenReturn(true);
        when(accountRepository.isNegativeBalance(accountId)).thenReturn(true);

        accountService.applyTransaction(transaction);

        verify(accountRepository, times(1)).withdrawFunds(accountId, transaction.getAmount());
        verify(accountRepository, times(1)).isNegativeBalance(accountId);
        verify(accountRepository, times(0)).addFunds(eq(accountId), any());
    }

    private Transaction buildTransaction(TransactionType transactionType) {
        return Transaction.builder()
                .id(155)
                .accountId(accountId)
                .type(transactionType)
                .amount(BigDecimal.ONE)
                .build();
    }
}
