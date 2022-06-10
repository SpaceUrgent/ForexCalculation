package Forex.DataContainers;

import java.math.BigDecimal;
import java.util.List;

public class TotalProfitLoss {

    public static BigDecimal calculateTotalProfitAndLost(List<TotalProfitLossForCurrency> profitAndLostTotalsForCurrencies){
        return profitAndLostTotalsForCurrencies.stream()
                .map(TotalProfitLossForCurrency::getTotalProfitLoss)
                .reduce(BigDecimal.ZERO ,BigDecimal::add);
    }
}
