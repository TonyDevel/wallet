package com.wallet.model.converter;

import com.wallet.model.dto.transaction.TransactionRequest;
import com.wallet.model.entity.Transaction;
import com.wallet.model.entity.TransactionType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.wallet.util.MoneyUtils.setMoneyScale;

@Component
public class TransactionRequestToEntityConverter implements Converter<TransactionRequest, Transaction> {

    @Override
    public Transaction convert(TransactionRequest transactionRequest) {
        return Transaction.builder()
                .id(transactionRequest.getTransactionId())
                .type(TransactionType.valueOfIgnoreCase(transactionRequest.getTransactionType()))
                .accountId(transactionRequest.getAccountId())
                .amount(setMoneyScale(transactionRequest.getAmount()))
                .build();
    }
}
