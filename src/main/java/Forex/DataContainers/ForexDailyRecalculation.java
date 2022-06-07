package Forex.DataContainers;

import java.math.BigDecimal;
import java.util.Date;

public class ForexDailyRecalculation {

    String currency;
    Date date;
    BigDecimal currencyRate;
    BigDecimal openingBalance;
    BigDecimal dailyDebitTurnover;
    BigDecimal dailyCreditTurnover;
    BigDecimal closingBalance;
    BigDecimal profitLossInUah;

    public ForexDailyRecalculation(Date date){
        this.date = date;
    }

    public ForexDailyRecalculation(){
    }

    public ForexDailyRecalculation(String currency, Date date, BigDecimal currencyRate, BigDecimal openingBalance, BigDecimal dailyDebitTurnover, BigDecimal dailyCreditTurnover, BigDecimal closingBalance, BigDecimal profitLossInUah) {
        this.currency = currency;
        this.date = date;
        this.currencyRate = currencyRate;
        this.openingBalance = openingBalance;
        this.dailyDebitTurnover = dailyDebitTurnover;
        this.dailyCreditTurnover = dailyCreditTurnover;
        this.closingBalance = closingBalance;
        this.profitLossInUah = profitLossInUah;
    }

    @Override
    public String toString() {
        return "ForexDailyRecalculation{" +
                "currency='" + currency + '\'' +
                ", date=" + date +
                ", currencyRate=" + currencyRate +
                ", openingBalance=" + openingBalance +
                ", dailyDebitTurnover=" + dailyDebitTurnover +
                ", dailyCreditTurnover=" + dailyCreditTurnover +
                ", closingBalance=" + closingBalance +
                ", profitLossInUah=" + profitLossInUah +
                '}';
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(BigDecimal currencyRate) {
        this.currencyRate = currencyRate;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getDailyDebitTurnover() {
        return dailyDebitTurnover;
    }

    public void setDailyDebitTurnover(BigDecimal dailyDebitTurnover) {
        this.dailyDebitTurnover = dailyDebitTurnover;
    }

    public BigDecimal getDailyCreditTurnover() {
        return dailyCreditTurnover;
    }

    public void setDailyCreditTurnover(BigDecimal dailyCreditTurnover) {
        this.dailyCreditTurnover = dailyCreditTurnover;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
    }

    public BigDecimal getProfitLossInUah() {
        return profitLossInUah;
    }

    public void setProfitLossInUah(BigDecimal profitLossInUah) {
        this.profitLossInUah = profitLossInUah;
    }
}
