package com.wallet.model.dto.account;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterAccountRequest {
    @Min(1)
    @NotNull
    private Integer playerId;
}
