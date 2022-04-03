package com.wallet.log;

import com.wallet.model.dto.transaction.TransactionRequest;
import com.wallet.model.entity.Transaction;
import java.math.BigDecimal;
import lombok.Data;

/**
 * Log context to keep the context in one convenient object
 * toString is overridden by data and provide us a good view on the object
 */
@Data
public class LogContext {

    private Integer accountId;
    private Integer transactionId;
    private String transactionType;
    private BigDecimal transactionAmount;


    public static LogContext of(TransactionRequest request) {
        var ctx = new LogContext();
        ctx.setAccountId(request.getAccountId());
        ctx.setTransactionId(request.getTransactionId());
        ctx.setTransactionType(request.getTransactionType());
        ctx.setTransactionAmount(request.getAmount());
        return ctx;
    }

    public static LogContext of(Transaction transaction) {
        var ctx = new LogContext();
        ctx.setAccountId(transaction.getAccountId());
        ctx.setTransactionId(transaction.getId());
        ctx.setTransactionType(transaction.getType().name());
        ctx.setTransactionAmount(transaction.getAmount());
        return ctx;
    }
}
