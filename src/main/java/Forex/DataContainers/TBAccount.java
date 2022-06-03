package Forex.DataContainers;

import java.math.BigDecimal;

public class TBAccount {

    String accountId;
    String accountDescription;
    String currency;
    String currencyCode;
    String openingBalance;
    String debitTurnover;
    String creditTurnover;
    String closingBalance;

    public TBAccount(String accountId, String accountDescription, String currency, String currencyCode, String openingBalance, String debitTurnover, String creditTurnover, String closingBalance) {
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
