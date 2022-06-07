package Forex.DataContainers;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public class NBURate {
    Date date;
    String time;
    String currencyCode;
    String currency;
    String currencyAmount;
    String currencyUkr;
    BigDecimal currencyRate;

    public NBURate(Date date, String time, String currencyCode, String currency, String currencyAmount, String currencyUkr, BigDecimal currencyRate) {
        this.date = date;
        this.time = time;
        this.currencyCode = currencyCode;
        this.currency = currency;
        this.currencyAmount = currencyAmount;
        this.currencyUkr = currencyUkr;
        this.currencyRate = currencyRate;
    }

    @Override
    public String toString() {
        return "NBURate{" +
                "date=" + date +
                ", time=" + time +
                ", currencyCode='" + currencyCode + '\'' +
                ", currency='" + currency + '\'' +
                ", currencyAmount=" + currencyAmount +
                ", currencyUkr='" + currencyUkr + '\'' +
                ", currencyRate=" + currencyRate +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCurrencyAmount() {
        return currencyAmount;
    }

    public String getCurrencyUkr() {
        return currencyUkr;
    }

    public BigDecimal getCurrencyRate() {
        return currencyRate;
    }
}
