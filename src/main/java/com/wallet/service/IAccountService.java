package com.wallet.service;

import com.wallet.model.dto.account.AccountDTO;
import com.wallet.model.dto.account.RegisterAccountRequest;
import com.wallet.model.entity.Transaction;
import java.math.BigDecimal;

public interface IAccountService {
    boolean exists(Integer accountId);

    boolean sufficientFundsToWithdraw(Integer accountId, BigDecimal amount);

    void applyTransaction(Transaction transaction);

    AccountDTO register(RegisterAccountRequest request);

    AccountDTO getPlayerAccount(Integer playerId);
}
