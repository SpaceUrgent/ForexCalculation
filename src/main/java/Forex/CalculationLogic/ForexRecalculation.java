package Forex.CalculationLogic;

import Forex.DataContainers.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ForexRecalculation {

    List<NBURate> nbuRates;
    List<TBAccount> accountsTb;

    List<JERecord> journalEntries;

    public List<ForexDailyRecalculation> performForexDailyRecalculationsUsd(){
        List<Date> datesInYear = extractDates();
        List<ForexDailyRecalculation> emptyForexRecalculation = createEmptyDailyForex(datesInYear);
        return performDailyRecalculationForCurrency("USD", emptyForexRecalculation);
    }


    public List<ForexDailyRecalculation> performForexDailyRecalculationsEur(){
        List<Date> datesInYear = extractDates();
        List<ForexDailyRecalculation> emptyForexRecalculation = createEmptyDailyForex(datesInYear);
        return performDailyRecalculationForCurrency("EUR", emptyForexRecalculation);
    }

    public List<ForexDailyRecalculation> performForexDailyRecalculationsGbp(){
        List<Date> datesInYear = extractDates();
        List<ForexDailyRecalculation> emptyForexRecalculation = createEmptyDailyForex(datesInYear);
        return performDailyRecalculationForCurrency("GBP", emptyForexRecalculation);
    }


    public TotalProfitLoss calculateTotalProfitLoss(List<ForexDailyRecalculation> forexDailyRecalculations){
        String currency = forexDailyRecalculations.stream().iterator().next().getCurrency();
        BigDecimal totalPl = forexDailyRecalculations.stream()
                .map(f -> f.getProfitLossInUah())
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        return new TotalProfitLoss(currency, totalPl);
    }

    public List<DailyRecalculationReconciliation> reconcileDailyCalculationToTb(List<ForexDailyRecalculation> forexDailyRecalculations){
        List<DailyRecalculationReconciliation> reconciliationToTb = new ArrayList<>();
        String currency = forexDailyRecalculations.stream().iterator().next().getCurrency();
        DailyRecalculationReconciliation perCalculations = calculateBalancesPerDailyRecalculation(forexDailyRecalculations);
        reconciliationToTb.add(perCalculations);
        DailyRecalculationReconciliation perTB = extractBalancesFromTb(currency);
        reconciliationToTb.add(perTB);
        DailyRecalculationReconciliation checkLine = checkCalculationsToTb(perCalculations, perTB);
        reconciliationToTb.add(checkLine);
        return  reconciliationToTb;

    }

    private DailyRecalculationReconciliation checkCalculationsToTb(DailyRecalculationReconciliation perCalculations, DailyRecalculationReconciliation perTB) {
        String currency = perCalculations.getCurrency();
        String description = "Check";
        BigDecimal openingBalance = perCalculations.getOpeningBalance().subtract(perTB.getOpeningBalance());
        BigDecimal debitTurnover = perCalculations.getDebitTurnover().subtract(perTB.getDebitTurnover());
        BigDecimal creditTurnover = perCalculations.getCreditTurnover().subtract(perTB.getCreditTurnover());
        BigDecimal closingBalance = perCalculations.getClosingBalance().subtract(perTB.getClosingBalance());
        return  new DailyRecalculationReconciliation(currency, description, openingBalance, debitTurnover, creditTurnover, closingBalance);
    }

    private DailyRecalculationReconciliation calculateBalancesPerDailyRecalculation(List<ForexDailyRecalculation> forexDailyRecalculations) {
        String currency = forexDailyRecalculations.stream().iterator().next().getCurrency();
        String description = "Per Calc";
        BigDecimal openingBalance = forexDailyRecalculations.stream().min(Comparator.comparing(d -> d.getDate())).get().getOpeningBalance();
        BigDecimal debitTurnover = forexDailyRecalculations.stream().map(ob -> ob.getDailyDebitTurnover()).reduce(BigDecimal.ZERO, (a,b) -> a.add(b));
        BigDecimal creditTurnover = forexDailyRecalculations.stream().map(ob -> ob.getDailyCreditTurnover()).reduce(BigDecimal.ZERO, (a,b) -> a.add(b));
        BigDecimal closingBalance = forexDailyRecalculations.stream().max(Comparator.comparing(d -> d.getDate())).get().getClosingBalance();
        return  new DailyRecalculationReconciliation(currency, description, openingBalance, debitTurnover, creditTurnover, closingBalance);
    }

    private DailyRecalculationReconciliation extractBalancesFromTb(String currency) {
        String description = "Per TB";
        BigDecimal openingBalance = accountsTb.stream().filter(x -> x.getCurrency().equals(currency)).findFirst().get().getOpeningBalance();
        BigDecimal debitTurnover = accountsTb.stream().filter(x -> x.getCurrency().equals(currency)).findFirst().get().getDebitTurnover();
        BigDecimal creditTurnover = accountsTb.stream().filter(x -> x.getCurrency().equals(currency)).findFirst().get().getCreditTurnover();
        BigDecimal closingBalance = accountsTb.stream().filter(x -> x.getCurrency().equals(currency)).findFirst().get().getClosingBalance();
        return new DailyRecalculationReconciliation(currency, description, openingBalance, debitTurnover, creditTurnover, closingBalance);
    }

    public List<Date> extractDates(){
        return nbuRates.stream()
                .map(d -> d.getDate())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<ForexDailyRecalculation> createEmptyDailyForex(List<Date> dates){
        List<ForexDailyRecalculation> result = new ArrayList<>();
        for (Date it: dates) {
            result.add(new ForexDailyRecalculation(it));
        }
        return result;
    }

    public List<ForexDailyRecalculation> performDailyRecalculationForCurrency(String currency, List<ForexDailyRecalculation> emptyList){
        Iterator<ForexDailyRecalculation> iterator = emptyList.iterator();
        ForexDailyRecalculation previousLine = new ForexDailyRecalculation();
        List<ForexDailyRecalculation> dailyRecalculations = new ArrayList<>();

        while (iterator.hasNext()) {
            ForexDailyRecalculation line = iterator.next();
            line.setCurrency(currency);
            line.setCurrencyRate(extractCurrencyRateByDate(currency, line.getDate()));
            line.setOpeningBalance(calculateOpeningBalance(currency, previousLine));
            line.setDailyDebitTurnover(calculateDebitTurnover(currency, line.getDate()));
            line.setDailyCreditTurnover(calculateCreditTurnover(currency, line.getDate()));
            line.setClosingBalance(calculateClosingBalance(line));
            line.setProfitLossInUah(calculateProfitLoss(line, previousLine));

            dailyRecalculations.add(line);
            previousLine = line;
        }
        return dailyRecalculations;
    }

    public BigDecimal calculateProfitLoss(ForexDailyRecalculation line, ForexDailyRecalculation previousLine) {
        if (previousLine.getCurrencyRate() == null)
            return BigDecimal.valueOf(0);
        BigDecimal currencyDiff = line.getCurrencyRate().subtract(previousLine.getCurrencyRate());
        BigDecimal changeInBalance = line.getClosingBalance().subtract(line.getOpeningBalance());
        return currencyDiff.multiply(changeInBalance);
    }

    public BigDecimal calculateClosingBalance(ForexDailyRecalculation line) {

//        result.add(line.getOpeningBalance());
//        result.add(line.getDailyDebitTurnover());
//        result.add(line.getDailyCreditTurnover());
        BigDecimal ob = line.getOpeningBalance();
        BigDecimal dr = line.getDailyDebitTurnover();
        BigDecimal cr = line.getDailyCreditTurnover();
        BigDecimal result = (ob).add(dr).add(cr);
        return result;
    }


    public BigDecimal calculateDebitTurnover(String currency, Date date) {
        return BigDecimal.valueOf(journalEntries.stream()
                .filter(c -> c.getCurrency().equals(currency))
                .filter(d -> d.getRecordDate().equals(date))
                .filter(type -> type.getEntryType().equals("DR"))
                .mapToDouble(je -> je.getAmount().doubleValue())
                .sum());
    }

    public BigDecimal calculateCreditTurnover(String currency, Date date) {
        return BigDecimal.valueOf(journalEntries.stream()
                .filter(c -> c.getCurrency().equals(currency))
                .filter(d -> d.getRecordDate().equals(date))
                .filter(type -> type.getEntryType().equals("CR"))
                .mapToDouble(je -> je.getAmount().doubleValue())
                .sum());
    }

    public BigDecimal extractCurrencyRateByDate(String currency, Date date) {
        return nbuRates.stream()
                .filter(c -> c.getCurrency().equals(currency))
                .filter(d -> d.getDate().equals(date))
                .findAny()
                .get().getCurrencyRate();
    }

    public BigDecimal calculateOpeningBalance(String currency, ForexDailyRecalculation previousLine){
        if (previousLine.getClosingBalance() == null)
            return extractOpeningBalanceFromTb(currency);
        return previousLine.getClosingBalance();
    }

    private BigDecimal extractOpeningBalanceFromTb(String currency) {
        return accountsTb.stream()
                .filter(c -> c.getCurrency().equals(currency))
                .findAny()
                .get().getOpeningBalance();
    }


    public ForexRecalculation(List<NBURate> nbuRates, List<TBAccount> accountsTb, List<JERecord> journalEntries) {
        this.nbuRates = nbuRates;
        this.accountsTb = accountsTb;
        this.journalEntries = journalEntries;
    }
}
