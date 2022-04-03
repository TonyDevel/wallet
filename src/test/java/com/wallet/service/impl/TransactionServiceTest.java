package com.wallet.service.impl;

import com.wallet.ConversionTestUtil;
import com.wallet.model.converter.TransactionRequestToEntityConverter;
import com.wallet.model.converter.TransactionToDtoConverter;
import com.wallet.model.dto.transaction.TransactionDTO;
import com.wallet.model.dto.transaction.TransactionRequest;
import com.wallet.model.entity.Transaction;
import com.wallet.model.entity.TransactionType;
import com.wallet.repository.TransactionRepository;
import com.wallet.service.IAccountService;
import com.wallet.util.MoneyUtils;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    private final Integer transactionId = 31432;
    private final Integer playerId = 232;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private IAccountService accountService;

    @Spy
    private ConversionService conversionService = ConversionTestUtil.initConverters(
            new TransactionRequestToEntityConverter(),
            new TransactionToDtoConverter()
    );

    @Test
    public void createTest() {
        Integer accountId = 15;
        String transactionType = "DEBIT";
        BigDecimal transactionAmount = MoneyUtils.setMoneyScale(BigDecimal.TEN);
        TransactionRequest request = new TransactionRequest();
        request.setTransactionId(transactionId);
        request.setAccountId(accountId);
        request.setTransactionType(transactionType);
        request.setAmount(transactionAmount);
        when(transactionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        TransactionDTO transactionDTO = transactionService.create(request);

        assertNotNull(transactionDTO);
        assertEquals(transactionId, transactionDTO.getId());
        assertEquals(accountId, transactionDTO.getAccountId());
        assertEquals(TransactionType.valueOf(transactionType), transactionDTO.getType());
        assertEquals(transactionAmount, transactionDTO.getAmount());

        verify(transactionRepository, times(1)).save(any());
        verify(accountService, times(1)).applyTransaction(any());
    }

    @Test
    public void existsTest() {
        when(transactionRepository.existsById(transactionId)).thenReturn(true);

        boolean exists = transactionService.exists(transactionId);

        assertTrue(exists);
        verify(transactionRepository, times(1)).existsById(transactionId);
    }

    @Test
    public void getTransactionHistoryTest() {
        Transaction transaction1 = Transaction.builder()
                .id(15)
                .accountId(25)
                .type(TransactionType.CREDIT)
                .amount(BigDecimal.ONE)
                .build();
        Transaction transaction2 = Transaction.builder()
                .id(16)
                .accountId(25)
                .type(TransactionType.DEBIT)
                .amount(BigDecimal.ONE)
                .build();

        when(transactionRepository.findAllByPlayerId(playerId)).thenReturn(List.of(transaction1, transaction2));

        List<TransactionDTO> transactionHistory = transactionService.getTransactionHistory(playerId);
        assertEquals(2, transactionHistory.size());
        assertTransaction(transaction1, transactionHistory.get(0));
        assertTransaction(transaction2, transactionHistory.get(1));
        verify(transactionRepository, times(1)).findAllByPlayerId(playerId);
    }

    private void assertTransaction(Transaction expected, TransactionDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAccountId(), actual.getAccountId());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getAmount(), actual.getAmount());
    }
}
