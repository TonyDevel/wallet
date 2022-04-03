package com.wallet.model.converter;

import com.wallet.model.dto.account.AccountDTO;
import com.wallet.model.entity.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountToDTOConverter implements Converter<Account, AccountDTO> {

    @Override
    public AccountDTO convert(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .playerId(account.getPlayerId())
                .balance(account.getAmount())
                .build();
    }
}
