package com.wallet.model.converter;

import com.wallet.model.dto.account.RegisterAccountRequest;
import com.wallet.model.entity.Account;
import java.math.BigDecimal;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.wallet.util.MoneyUtils.setMoneyScale;

@Component
public class RegisterAccountRequestToEntityConverter implements Converter<RegisterAccountRequest, Account> {

    @Override
    public Account convert(RegisterAccountRequest request) {
        return Account.builder()
                .playerId(request.getPlayerId())
                .amount(setMoneyScale(BigDecimal.ZERO))
                .build();
    }
}
