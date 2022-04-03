package com.wallet.model.validation.validator;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidTransactionTypeValidatorTest {

    private ValidTransactionTypeValidator validator = new ValidTransactionTypeValidator();

    @Test
    public void validTest() {
        boolean valid = validator.isValid("DEBIT", null);
        assertTrue(valid);

        valid = validator.isValid("credit", null);
        assertTrue(valid);

        valid = validator.isValid("add", null);
        assertFalse(valid);
    }
}
