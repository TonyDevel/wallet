package com.wallet.service.impl;

import com.wallet.log.LogContext;
import com.wallet.model.dto.transaction.TransactionDTO;
import com.wallet.model.dto.transaction.TransactionRequest;
import com.wallet.model.entity.Transaction;
import com.wallet.repository.TransactionRepository;
import com.wallet.service.IAccountService;
import com.wallet.service.ITransactionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final IAccountService accountService;
    private final ConversionService conversionService;

    /**
     * Register transaction
     * We rely on ACID compliance of underlying database (Postgres)
     * If contention is high (I suppose so), it's better to use READ_COMMITTED + select for update
     * If contention is low we can use the most strict isolation level SERIALIZABLE to deal with concurrency
     *
     * @param request - create transaction request
     * @return transaction dto
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public TransactionDTO create(TransactionRequest request) {
        var ctx = LogContext.of(request);
        log.info("Start to create transaction {}", ctx);
        Transaction transaction = conversionService.convert(request, Transaction.class);
        transactionRepository.save(transaction);
        accountService.applyTransaction(transaction);
        log.info("Transaction has successfully been created {}", ctx);
        return conversionService.convert(transaction, TransactionDTO.class);
    }

    /**
     * Check if transaction exists
     *
     * @param transactionId - transaction id
     * @return result if transaction exists
     */
    @Override
    public boolean exists(Integer transactionId) {
        return transactionRepository.existsById(transactionId);
    }

    @Override
    public List<TransactionDTO> getTransactionHistory(Integer playerId) {
        log.info("Looking for transactions for the player with id: {}", playerId);
        List<Transaction> transactions = transactionRepository.findAllByPlayerId(playerId);
        log.info("Found {} transactions for the player with id: {}", transactions.size(), playerId);
        return transactions.stream()
                .map(t -> conversionService.convert(t, TransactionDTO.class))
                .collect(Collectors.toList());
    }
}
