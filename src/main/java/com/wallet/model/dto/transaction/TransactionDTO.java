package com.wallet.model.dto.transaction;

import com.wallet.model.entity.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {
    @Schema(description = "Transaction identifier")
    private Integer id;
    @Schema(description = "Account identifier")
    private Integer accountId;
    @Schema(description = "Type of transaction")
    private TransactionType type;
    @Schema(description = "Amount of transaction")
    private BigDecimal amount;
}
