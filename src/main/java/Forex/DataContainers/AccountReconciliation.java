package Forex.CalculationLogic;

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
}
