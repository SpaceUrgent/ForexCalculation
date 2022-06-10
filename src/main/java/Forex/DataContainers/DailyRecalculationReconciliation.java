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

    public DailyRecalculationReconciliation(DailyRecalculationReconciliationBuilder builder) {
        this.currency = builder.currency;
        this.description = builder.description;
        this.openingBalance = builder.openingBalance;
        this.debitTurnover = builder.debitTurnover;
        this.creditTurnover = builder.creditTurnover;
        this.closingBalance = builder.closingBalance;
    }


    public static class DailyRecalculationReconciliationBuilder{

        private String currency;
        private String description;
        private BigDecimal openingBalance;
        private BigDecimal debitTurnover;
        private BigDecimal creditTurnover;
        private BigDecimal closingBalance;

        public DailyRecalculationReconciliationBuilder(){
        }

        public DailyRecalculationReconciliationBuilder currency(String currency){
            this.currency = currency;
            return this;
        }

        public DailyRecalculationReconciliationBuilder description(String description){
            this.description = description;
            return this;
        }

        public DailyRecalculationReconciliationBuilder openingBalance(BigDecimal openingBalance){
            this.openingBalance = openingBalance;
            return this;
        }

        public DailyRecalculationReconciliationBuilder debitTurnover(BigDecimal debitTurnover){
            this.debitTurnover = debitTurnover;
            return this;
        }

        public DailyRecalculationReconciliationBuilder creditTurnover(BigDecimal creditTurnover){
            this.creditTurnover = creditTurnover;
            return this;
        }

        public DailyRecalculationReconciliationBuilder closingBalance(BigDecimal closingBalance){
            this.closingBalance = closingBalance;
            return this;
        }

        public DailyRecalculationReconciliation build(){
            DailyRecalculationReconciliation dailyRecalculationReconciliation = new DailyRecalculationReconciliation(this);
            return dailyRecalculationReconciliation;
        }
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
