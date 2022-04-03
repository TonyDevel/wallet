package com.wallet.service;

import com.wallet.model.dto.transaction.TransactionDTO;
import com.wallet.model.dto.transaction.TransactionRequest;
import java.util.List;

public interface ITransactionService {
    TransactionDTO create(TransactionRequest request);

    boolean exists(Integer transactionId);

    List<TransactionDTO> getTransactionHistory(Integer playerId);
}
