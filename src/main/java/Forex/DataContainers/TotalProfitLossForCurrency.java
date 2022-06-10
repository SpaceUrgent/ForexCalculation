package Forex.DataContainers;

import java.math.BigDecimal;

public class TotalProfitLossForCurrency {
    String currency;
    BigDecimal totalProfitLoss;

    public TotalProfitLossForCurrency(String currency, BigDecimal totalProfitLoss) {
        this.currency = currency;
        this.totalProfitLoss = totalProfitLoss;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public void setTotalProfitLoss(BigDecimal totalProfitLoss) {
        this.totalProfitLoss = totalProfitLoss;
    }

    @Override
    public String toString() {
        return "TotalProfitLoss{" +
                "currency='" + currency + '\'' +
                ", totalProfitLoss=" + totalProfitLoss +
                '}';
    }
}
