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

    public TBAccount(TBAccountBuilder builder) {
        this.accountId = builder.accountId;
        this.accountDescription = builder.accountDescription;
        this.currency = builder.currency;
        this.currencyCode = builder.currencyCode;
        this.openingBalance = builder.openingBalance;
        this.debitTurnover = builder.debitTurnover;
        this.creditTurnover = builder.creditTurnover;
        this.closingBalance = builder.closingBalance;
    }

    public static class TBAccountBuilder{
        private String accountId;
        private String accountDescription;
        private String currency;
        private String currencyCode;
        private BigDecimal openingBalance;
        private BigDecimal debitTurnover;
        private BigDecimal creditTurnover;
        private BigDecimal closingBalance;

        public TBAccountBuilder(){
        }

        public TBAccountBuilder accountId(String accountId){
            this.accountId = accountId;
            return this;
        }

        public TBAccountBuilder accountDescription(String accountDescription){
            this.accountDescription = accountDescription;
            return this;
        }

        public TBAccountBuilder currency(String currency){
            this.currency = currency;
            return this;
        }

        public TBAccountBuilder currencyCode(String currencyCode){
            this.currencyCode = currencyCode;
            return this;
        }

        public TBAccountBuilder openingBalance(BigDecimal openingBalance){
            this.openingBalance = openingBalance;
            return this;
        }

        public TBAccountBuilder debitTurnover(BigDecimal debitTurnover){
            this.debitTurnover = debitTurnover;
            return this;
        }

        public TBAccountBuilder creditTurnover(BigDecimal creditTurnover){
            this.creditTurnover = creditTurnover;
            return this;
        }

        public TBAccountBuilder closingBalance(BigDecimal closingBalance){
            this.closingBalance = closingBalance;
            return this;
        }

        public TBAccount build(){
            TBAccount tbAccount = new TBAccount(this);
            return tbAccount;
        }
    }

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



    @Override
    public String toString() {
        return "TBAccount{" + "accountId=" + accountId + ", accountDescription='" + accountDescription + '\'' + ", currency='" + currency + '\'' + ", currencyCode='" + currencyCode + '\'' + ", openingBalance=" + openingBalance + ", debitTurnover=" + debitTurnover + ", creditTurnover=" + creditTurnover + ", closingBalance=" + closingBalance + '}';
    }
}
