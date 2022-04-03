package com.wallet.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoneyUtils {

    private static final int DEFAULT_MONEY_SCALE = 2;
    private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    // we don't need an instance of util class
    private MoneyUtils() {
    }

    public static BigDecimal setMoneyScale(BigDecimal value) {
        return value.setScale(DEFAULT_MONEY_SCALE, DEFAULT_ROUNDING_MODE);
    }
}
