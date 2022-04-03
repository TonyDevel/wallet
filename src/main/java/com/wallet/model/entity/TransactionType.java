package com.wallet.model.entity;

public enum TransactionType {
    DEBIT, CREDIT;

    public static TransactionType valueOfIgnoreCase(String transactionType) {
        return TransactionType.valueOf(transactionType.toUpperCase());
    }
}
