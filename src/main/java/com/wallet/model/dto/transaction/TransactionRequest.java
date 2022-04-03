package com.wallet.model.dto.transaction;

import com.wallet.model.validation.constraint.AccountExists;
import com.wallet.model.validation.constraint.SufficientFunds;
import com.wallet.model.validation.constraint.TransactionDoesNotExists;
import com.wallet.model.validation.constraint.ValidTransactionType;
import com.wallet.model.validation.constraint.group.SecondValidationGroup;
import javax.validation.GroupSequence;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@GroupSequence({TransactionRequest.class, SecondValidationGroup.class})
@SufficientFunds(groups = SecondValidationGroup.class)
@Data
public class TransactionRequest {

    @TransactionDoesNotExists
    @NotNull
    @Min(1)
    private Integer transactionId;

    @AccountExists
    @NotNull
    @Min(1)
    private Integer accountId;

    @Min(0)
    @NotNull
    private BigDecimal amount;

    @ValidTransactionType
    @NotBlank
    private String transactionType;
}
