package Forex.DataContainers;

import java.math.BigDecimal;

public class AccountReconciliation {
    String accountId;
    String accountDescription;
    String accountCurrency;
    String currencyCode;
    BigDecimal openingBalancePerTb;
    BigDecimal debitTurnoverPerTb;
    BigDecimal creditTurnoverPerTb;
    BigDecimal closingBalancePerTb;
    BigDecimal debitTurnoverPerJe;
    BigDecimal creditTurnoverPerJe;
    BigDecimal debitTurnoverDifference;
    BigDecimal creditTurnoverDifference;

    public AccountReconciliation(String accountId, String accountDescription, String accountCurrency, String currencyCode, BigDecimal openingBalancePerTb, BigDecimal debitTurnoverPerTb, BigDecimal creditTurnoverPerTb, BigDecimal closingBalancePerTb, BigDecimal debitTurnoverPerJe, BigDecimal creditTurnoverPerJe, BigDecimal debitTurnoverDifference, BigDecimal creditTurnoverDifference) {
        this.accountId = accountId;
        this.accountDescription = accountDescription;
        this.accountCurrency = accountCurrency;
        this.currencyCode = currencyCode;
        this.openingBalancePerTb = openingBalancePerTb;
        this.debitTurnoverPerTb = debitTurnoverPerTb;
        this.creditTurnoverPerTb = creditTurnoverPerTb;
        this.closingBalancePerTb = closingBalancePerTb;
        this.debitTurnoverPerJe = debitTurnoverPerJe;
        this.creditTurnoverPerJe = creditTurnoverPerJe;
        this.debitTurnoverDifference = debitTurnoverDifference;
        this.creditTurnoverDifference = creditTurnoverDifference;
    }

    @Override
    public String toString() {
        return "AccountReconciliation{" +
                "accountId='" + accountId + '\'' +
                ", accountDescription='" + accountDescription + '\'' +
                ", accountCurrency='" + accountCurrency + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", openingBalancePerTb=" + openingBalancePerTb +
                ", debitTurnoverPerTb=" + debitTurnoverPerTb +
                ", creditTurnoverPerTb=" + creditTurnoverPerTb +
                ", closingBalancePerTb=" + closingBalancePerTb +
                ", debitTurnoverPerJe=" + debitTurnoverPerJe +
                ", creditTurnoverPerJe=" + creditTurnoverPerJe +
                ", debitTurnoverDifference=" + debitTurnoverDifference +
                ", creditTurnoverDifference=" + creditTurnoverDifference +
                '}';
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountDescription() {
        return accountDescription;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getOpeningBalancePerTb() {
        return openingBalancePerTb;
    }

    public BigDecimal getDebitTurnoverPerTb() {
        return debitTurnoverPerTb;
    }

    public BigDecimal getCreditTurnoverPerTb() {
        return creditTurnoverPerTb;
    }

    public BigDecimal getClosingBalancePerTb() {
        return closingBalancePerTb;
    }

    public BigDecimal getDebitTurnoverPerJe() {
        return debitTurnoverPerJe;
    }

    public BigDecimal getCreditTurnoverPerJe() {
        return creditTurnoverPerJe;
    }

    public BigDecimal getDebitTurnoverDifference() {
        return debitTurnoverDifference;
    }

    public BigDecimal getCreditTurnoverDifference() {
        return creditTurnoverDifference;
    }
}
