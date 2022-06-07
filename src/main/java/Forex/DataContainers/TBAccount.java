package Forex.DataContainers;

import java.math.BigDecimal;

public class TBAccount {

    String accountId;
    String accountDescription;
    String currency;
    String currencyCode;
    BigDecimal openingBalance;
    BigDecimal debitTurnover;
    BigDecimal creditTurnover;
    BigDecimal closingBalance;

    public String getAccountId() {
        return accountId;
    }

    public String getAccountDescription() {
        return accountDescription;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public BigDecimal getDebitTurnover() {
        return debitTurnover;
    }

    public BigDecimal getCreditTurnover() {
        return creditTurnover;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public TBAccount(String accountId, String accountDescription, String currency, String currencyCode, BigDecimal openingBalance, BigDecimal debitTurnover, BigDecimal creditTurnover, BigDecimal closingBalance) {
        this.accountId = accountId;
        this.accountDescription = accountDescription;
        this.currency = currency;
        this.currencyCode = currencyCode;
        this.openingBalance = openingBalance;
        this.debitTurnover = debitTurnover;
        this.creditTurnover = creditTurnover;
        this.closingBalance = closingBalance;
    }

    @Override
    public String toString() {
        return "TBAccount{" + "accountId=" + accountId + ", accountDescription='" + accountDescription + '\'' + ", currency='" + currency + '\'' + ", currencyCode='" + currencyCode + '\'' + ", openingBalance=" + openingBalance + ", debitTurnover=" + debitTurnover + ", creditTurnover=" + creditTurnover + ", closingBalance=" + closingBalance + '}';
    }
}
