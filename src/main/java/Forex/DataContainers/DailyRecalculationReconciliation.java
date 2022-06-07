package Forex.DataContainers;

import java.math.BigDecimal;

public class DailyRecalculationReconciliation {
    String currency;
    String description;
    BigDecimal openingBalance;
    BigDecimal debitTurnover;
    BigDecimal creditTurnover;
    BigDecimal closingBalance;

    public DailyRecalculationReconciliation(String currency, String description, BigDecimal openingBalance, BigDecimal debitTurnover, BigDecimal creditTurnover, BigDecimal closingBalance) {
        this.currency = currency;
        this.description = description;
        this.openingBalance = openingBalance;
        this.debitTurnover = debitTurnover;
        this.creditTurnover = creditTurnover;
        this.closingBalance = closingBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getDebitTurnover() {
        return debitTurnover;
    }

    public void setDebitTurnover(BigDecimal debitTurnover) {
        this.debitTurnover = debitTurnover;
    }

    public BigDecimal getCreditTurnover() {
        return creditTurnover;
    }

    public void setCreditTurnover(BigDecimal creditTurnover) {
        this.creditTurnover = creditTurnover;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
    }
}
