package com.wallet.service.impl;

import com.wallet.exception.AccountNotFoundException;
import com.wallet.exception.InsufficientFundsToWithdrawException;
import com.wallet.log.LogContext;
import com.wallet.model.dto.account.AccountDTO;
import com.wallet.model.dto.account.RegisterAccountRequest;
import com.wallet.model.entity.Account;
import com.wallet.model.entity.Transaction;
import com.wallet.model.entity.TransactionType;
import com.wallet.repository.AccountRepository;
import com.wallet.service.IAccountService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;
    private final ConversionService conversionService;

    @Override
    public boolean exists(Integer accountId) {
        return accountRepository.existsById(accountId);
    }

    @Override
    public boolean sufficientFundsToWithdraw(Integer accountId, BigDecimal amountToWithdraw) {
        log.info("Check if account with id: {} has sufficient funds to withdraw", accountId);
        boolean result = accountRepository.sufficientFundsToWithdraw(accountId, amountToWithdraw);
        log.info("Is sufficient funds to withdraw {} from account {}: {}", amountToWithdraw, accountId, result);
        return result;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void applyTransaction(Transaction transaction) {
        var ctx = LogContext.of(transaction);
        log.info("Start to apply transaction {}", ctx);
        TransactionType transactionType = transaction.getType();
        Integer accountId = transaction.getAccountId();
        BigDecimal transactionAmount = transaction.getAmount();
        if (TransactionType.CREDIT.equals(transactionType)) {
            accountRepository.addFunds(accountId, transactionAmount);
        } else if (TransactionType.DEBIT.equals(transactionType)) {
            validateFundsToWithdraw(accountId, transactionAmount);
            accountRepository.withdrawFunds(accountId, transactionAmount);
            // just to make sure other transaction didn't affect the current
            if (accountRepository.isNegativeBalance(accountId)) {
                log.error("Couldn't apply transaction {}", ctx);
                throw new InsufficientFundsToWithdrawException(accountId);
            }
        } else {
            log.error("Couldn't apply transaction {}", ctx);
            throw new UnsupportedOperationException("Unknown transaction type");
        }
        log.info("Transaction has successfully been applied {}", ctx);
    }

    @Override
    public AccountDTO register(RegisterAccountRequest request) {
        Integer playerId = request.getPlayerId();
        log.info("Start to register account for the player with id: {}", playerId);
        Account account = conversionService.convert(request, Account.class);
        account = accountRepository.save(account);
        log.info("Account with id: {} has been created for the player: {}", account.getId(), playerId);
        return conversionService.convert(account, AccountDTO.class);
    }

    @Override
    public AccountDTO getPlayerAccount(Integer playerId) {
        Account account = accountRepository.findByPlayerId(playerId);
        if (account == null) {
            log.error("Couldn't find account for the player with id: {}", playerId);
            throw new AccountNotFoundException(playerId);
        }
        return conversionService.convert(account, AccountDTO.class);
    }

    private void validateFundsToWithdraw(Integer accountId, BigDecimal amountToWithdraw) {
        if (!sufficientFundsToWithdraw(accountId, amountToWithdraw)) {
            throw new InsufficientFundsToWithdrawException(accountId);
        }
    }
}
