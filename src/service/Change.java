package service;

import java.math.BigDecimal;

public enum Change {
    ONE_PENCE(new BigDecimal("0.01")),
    TWO_PENCE(new BigDecimal("0.02")),
    FIVE_PENCE(new BigDecimal("0.05")),
    TEN_PENCE(new BigDecimal("0.10")),
    TWENTY_PENCE(new BigDecimal("0.20")),
    FIFTY_PENCE(new BigDecimal("0.50")),
    ONE_POUND(new BigDecimal("1.00")),
    TWO_POUNDS(new BigDecimal("2.00"));

    public final BigDecimal value;

    Change(BigDecimal value) {
        this.value = value;
    }
}