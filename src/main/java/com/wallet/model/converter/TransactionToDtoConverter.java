package com.wallet.model.converter;

import com.wallet.model.dto.transaction.TransactionDTO;
import com.wallet.model.entity.Transaction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionToDtoConverter implements Converter<Transaction, TransactionDTO> {

    @Override
    public TransactionDTO convert(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccountId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .build();
    }
}
