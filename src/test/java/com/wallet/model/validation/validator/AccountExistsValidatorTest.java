package com.wallet.model.validation.validator;

import com.wallet.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountExistsValidatorTest {

    private final Integer accountId = 2;

    @InjectMocks
    private AccountExistsValidator validator;

    @Mock
    private IAccountService accountService;

    @Test
    public void testValidPositive() {
        when(accountService.exists(accountId)).thenReturn(true);

        boolean valid = validator.isValid(accountId, null);

        assertTrue(valid);
        verify(accountService, times(1)).exists(accountId);
    }

    @Test
    public void testValidNegative() {
        when(accountService.exists(accountId)).thenReturn(false);

        boolean valid = validator.isValid(accountId, null);

        assertFalse(valid);
        verify(accountService, times(1)).exists(accountId);
    }
}
