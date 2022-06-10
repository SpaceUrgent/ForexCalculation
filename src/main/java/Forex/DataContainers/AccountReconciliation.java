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

    public AccountReconciliation(AccountReconciliationBuilder accountReconciliationBuilder){
        this.accountId = accountReconciliationBuilder.accountId;
        this.accountDescription = accountReconciliationBuilder.accountDescription;
        this.accountCurrency = accountReconciliationBuilder.accountCurrency;
        this.currencyCode = accountReconciliationBuilder.currencyCode;
        this.openingBalancePerTb = accountReconciliationBuilder.openingBalancePerTb;
        this.debitTurnoverPerTb = accountReconciliationBuilder.debitTurnoverPerTb;
        this.creditTurnoverPerTb = accountReconciliationBuilder.creditTurnoverPerTb;
        this.closingBalancePerTb = accountReconciliationBuilder.closingBalancePerTb;
        this.debitTurnoverPerJe = accountReconciliationBuilder.debitTurnoverPerJe;
        this.creditTurnoverPerJe = accountReconciliationBuilder.creditTurnoverPerJe;
        this.debitTurnoverDifference = accountReconciliationBuilder.debitTurnoverDifference;
        this.creditTurnoverDifference = accountReconciliationBuilder.creditTurnoverDifference;
    }

    public static class AccountReconciliationBuilder{
        private String accountId;
        private String accountDescription;
        private String accountCurrency;
        private String currencyCode;
        private BigDecimal openingBalancePerTb;
        private BigDecimal debitTurnoverPerTb;
        private BigDecimal creditTurnoverPerTb;
        private BigDecimal closingBalancePerTb;
        private BigDecimal debitTurnoverPerJe;
        private BigDecimal creditTurnoverPerJe;
        private BigDecimal debitTurnoverDifference;
        private BigDecimal creditTurnoverDifference;

        public AccountReconciliationBuilder(){
        }

        public AccountReconciliationBuilder accountId(String accountId){
            this.accountId = accountId;
            return this;
        }

        public AccountReconciliationBuilder accountDescription(String accountDescription){
            this.accountDescription = accountDescription;
            return this;
        }

        public AccountReconciliationBuilder accountCurrency(String accountCurrency){
            this.accountCurrency = accountCurrency;
            return this;
        }

        public AccountReconciliationBuilder currencyCode(String currencyCode){
            this.currencyCode = currencyCode;
            return this;
        }

        public AccountReconciliationBuilder openingBalancePerTb(BigDecimal openingBalancePerTb){
            this.openingBalancePerTb = openingBalancePerTb;
            return this;
        }

        public AccountReconciliationBuilder debitTurnoverPerTb(BigDecimal debitTurnoverPerTb){
            this.debitTurnoverPerTb = debitTurnoverPerTb;
            return this;
        }

        public AccountReconciliationBuilder creditTurnoverPerTb(BigDecimal creditTurnoverPerTb){
            this.creditTurnoverPerTb = creditTurnoverPerTb;
            return this;
        }

        public AccountReconciliationBuilder closingBalancePerTb(BigDecimal closingBalancePerTb){
            this.closingBalancePerTb = closingBalancePerTb;
            return this;
        }

        public AccountReconciliationBuilder debitTurnoverPerJe(BigDecimal debitTurnoverPerJe){
            this.debitTurnoverPerJe = debitTurnoverPerJe;
            return this;
        }

        public AccountReconciliationBuilder creditTurnoverPerJe(BigDecimal creditTurnoverPerJe){
            this.creditTurnoverPerJe = creditTurnoverPerJe;
            return this;
        }

        public AccountReconciliationBuilder debitTurnoverDifference(BigDecimal debitTurnoverDifference){
            this.debitTurnoverDifference = debitTurnoverDifference;
            return this;
        }

        public AccountReconciliationBuilder creditTurnoverDifference(BigDecimal creditTurnoverDifference){
            this.creditTurnoverDifference = creditTurnoverDifference;
            return this;
        }

        public AccountReconciliation build(){
            AccountReconciliation accountReconciliation = new AccountReconciliation(this);
            return accountReconciliation;
        }

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
