package com.wallet.exception;

public class InsufficientFundsToWithdrawException extends RuntimeException {

    private static final String DEFAULT_MESSAGE_TEMPLATE = "Insufficient funds to withdraw from account: %s";

    public InsufficientFundsToWithdrawException(Integer accountId) {
        super(String.format(DEFAULT_MESSAGE_TEMPLATE, accountId));
    }
}
