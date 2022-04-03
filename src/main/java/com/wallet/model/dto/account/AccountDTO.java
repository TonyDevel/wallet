package com.wallet.model.dto.account;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AccountDTO {
    private Integer id;
    private Integer playerId;
    private BigDecimal balance;
}
