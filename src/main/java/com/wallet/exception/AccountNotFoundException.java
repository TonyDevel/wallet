package com.wallet.exception;

public class AccountNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE_TEMPLATE = "Couldn't find account for player with id: %s";

    public AccountNotFoundException(Integer playerId) {
        super(String.format(DEFAULT_MESSAGE_TEMPLATE, playerId));
    }
}
