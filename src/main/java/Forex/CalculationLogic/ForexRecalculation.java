package Forex.CalculationLogic;

import Forex.Consants.EntryType;
import Forex.DataContainers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForexRecalculation {

    List<NBURate> nbuRates;
    List<TBAccount> accountsTb;

    List<JERecord> journalEntries;

    int currentYear;


    public ForexRecalculation(List<NBURate> nbuRates, List<TBAccount> accountsTb, List<JERecord> journalEntries, int currentYear) {
        this.nbuRates = nbuRates;
        this.accountsTb = accountsTb;
        this.journalEntries = journalEntries;
        this.currentYear = currentYear;
    }

    public List<ForexDailyRecalculation> performDailyRecalculationForAllCurrencies(){
        List<String> uniqueCurrencies = extractCurrenciesFromTrialBalance();

        List<ForexDailyRecalculation> dailyRecalculationsForAllCurrencies = new ArrayList<>();


        for (String currency: uniqueCurrencies) {
            List<ForexDailyRecalculation> dailyRecalculationsForCurrentCurrency = performDailyRecalculationForCurrency(currency);

            dailyRecalculationsForAllCurrencies.addAll(dailyRecalculationsForCurrentCurrency);
        }

        return dailyRecalculationsForAllCurrencies;
    }


    private List<String> extractCurrenciesFromTrialBalance() {
        List<String> uniqueCurrencies = accountsTb.stream()
                .map(TBAccount::getCurrency)
                .distinct()
                .collect(Collectors.toList());
        return uniqueCurrencies;
    }

    public List<TotalProfitLossForCurrency> calculateTotalProfitLoss(List<ForexDailyRecalculation> forexDailyRecalculations){
        List<String> uniqueCurrencies = extractCurrenciesFromTrialBalance();
        List<TotalProfitLossForCurrency> profitLossTotals = new ArrayList<>();
        for (String currency: uniqueCurrencies) {
            TotalProfitLossForCurrency totalProfitLossForCurrentCurrency = calculateTotalProfitLossForCurrency(currency, forexDailyRecalculations);
            profitLossTotals.add(totalProfitLossForCurrentCurrency);
        }
        return profitLossTotals;
    }

    public TotalProfitLossForCurrency calculateTotalProfitLossForCurrency(String currency,List<ForexDailyRecalculation> forexDailyRecalculations){

        BigDecimal totalPl = forexDailyRecalculations.stream()
                .filter(forexDailyRecalculation -> forexDailyRecalculation.getCurrency().equals(currency))
                .map(ForexDailyRecalculation::getProfitLossInUah)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new TotalProfitLossForCurrency(currency, totalPl);
    }

    public List<DailyRecalculationReconciliation> reconcileDailyCalculationToTrialBalance(List<ForexDailyRecalculation> forexDailyRecalculations){
        List<DailyRecalculationReconciliation> dailyRecalculationReconciliations = new ArrayList<>();
        List<String> uniqueCurrencies = extractCurrenciesFromTrialBalance();

        for (String currentCurrency: uniqueCurrencies) {
            DailyRecalculationReconciliation dailyRecalculationReconciliationPerCalculation = calculateBalancesPerDailyRecalculation(currentCurrency, forexDailyRecalculations);
            dailyRecalculationReconciliations.add(dailyRecalculationReconciliationPerCalculation);
            DailyRecalculationReconciliation dailyRecalculationReconciliationPerTrialBalance = extractBalancesFromTrialBalance(currentCurrency);
            dailyRecalculationReconciliations.add(dailyRecalculationReconciliationPerTrialBalance);
            DailyRecalculationReconciliation dailyRecalculationCheckToTrialBalance = checkCalculationsToTrialBalance(dailyRecalculationReconciliationPerCalculation, dailyRecalculationReconciliationPerTrialBalance);
            dailyRecalculationReconciliations.add(dailyRecalculationCheckToTrialBalance);
        }
        return dailyRecalculationReconciliations;
    }

//    public List<DailyRecalculationReconciliation> reconcileDailyCalculationToTrialBalance(List<ForexDailyRecalculation> forexDailyRecalculations){
//        List<DailyRecalculationReconciliation> reconciliationToTb = new ArrayList<>();
//        String currency = forexDailyRecalculations.get(0).getCurrency();
//        DailyRecalculationReconciliation perCalculations = calculateBalancesPerDailyRecalculation(forexDailyRecalculations);
//        reconciliationToTb.add(perCalculations);
//        DailyRecalculationReconciliation perTB = extractBalancesFromTrialBalance(currency);
//        reconciliationToTb.add(perTB);
//        DailyRecalculationReconciliation checkPerCalculationFieldToPerTrialBalance = checkCalculationsToTb(perCalculations, perTB);
//        reconciliationToTb.add(checkPerCalculationFieldToPerTrialBalance);
//        return  reconciliationToTb;
//
//    }

    private DailyRecalculationReconciliation checkCalculationsToTrialBalance(DailyRecalculationReconciliation perCalculations, DailyRecalculationReconciliation perTB) {
        String currency = perCalculations.getCurrency();
        String description = "Check";
        BigDecimal openingBalance = perCalculations.getOpeningBalance().subtract(perTB.getOpeningBalance());
        BigDecimal debitTurnover = perCalculations.getDebitTurnover().subtract(perTB.getDebitTurnover());
        BigDecimal creditTurnover = perCalculations.getCreditTurnover().subtract(perTB.getCreditTurnover());
        BigDecimal closingBalance = perCalculations.getClosingBalance().subtract(perTB.getClosingBalance());
        return  new DailyRecalculationReconciliation(currency, description, openingBalance, debitTurnover, creditTurnover, closingBalance);
    }

    private DailyRecalculationReconciliation calculateBalancesPerDailyRecalculation(String currency ,List<ForexDailyRecalculation> forexDailyRecalculations) {
        List<ForexDailyRecalculation> forexDailyRecalculationsPerCurrentCurrency = forexDailyRecalculations.stream()
                .filter(dailyRecalculation -> dailyRecalculation.getCurrency().equals(currency))
                .collect(Collectors.toList());
        String description = "Per Calc";
        BigDecimal openingBalance = forexDailyRecalculationsPerCurrentCurrency.stream().min(Comparator.comparing(ForexDailyRecalculation::getDate)).get().getOpeningBalance();
        BigDecimal debitTurnover = forexDailyRecalculationsPerCurrentCurrency.stream().map(ForexDailyRecalculation::getDailyDebitTurnover).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        BigDecimal creditTurnover = forexDailyRecalculationsPerCurrentCurrency.stream().map(ForexDailyRecalculation::getDailyCreditTurnover).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        BigDecimal closingBalance = forexDailyRecalculationsPerCurrentCurrency.stream().max(Comparator.comparing(ForexDailyRecalculation::getDate)).get().getClosingBalance();
        return  new DailyRecalculationReconciliation(currency, description, openingBalance, debitTurnover, creditTurnover, closingBalance);
    }

    private DailyRecalculationReconciliation extractBalancesFromTrialBalance(String currency) {
        String description = "Per TB";
        TBAccount trialBalanceAccount = accountsTb.stream().filter(tbAccount -> tbAccount.getCurrency().equals(currency)).findFirst().get();
        BigDecimal openingBalance = trialBalanceAccount.getOpeningBalance();
        BigDecimal debitTurnover = trialBalanceAccount.getDebitTurnover();
        BigDecimal creditTurnover = trialBalanceAccount.getCreditTurnover();
        BigDecimal closingBalance = trialBalanceAccount.getClosingBalance();
        return new DailyRecalculationReconciliation(currency, description, openingBalance, debitTurnover, creditTurnover, closingBalance);
    }

    public List<ForexDailyRecalculation> performDailyRecalculationForCurrency(String currency){

        ForexDailyRecalculation previousDayForexRecalculation = new ForexDailyRecalculation();
        List<ForexDailyRecalculation> dailyRecalculations = new ArrayList<>();

        LocalDate currentDate = LocalDate.of(currentYear, 1, 1);
        do {
            ForexDailyRecalculation currentDayForexRecalculation = new ForexDailyRecalculation();

            currentDayForexRecalculation.setCurrency(currency);
            currentDayForexRecalculation.setDate(convertToDate(currentDate));
            currentDayForexRecalculation.setCurrencyRate(extractCurrencyRateByDate(currency, convertToDate(currentDate)));
            currentDayForexRecalculation.setOpeningBalance(calculateOpeningBalance(currency, previousDayForexRecalculation));
            currentDayForexRecalculation.setDailyDebitTurnover(calculateTurnoverByType(currency, convertToDate(currentDate), EntryType.DR));
            currentDayForexRecalculation.setDailyCreditTurnover(calculateTurnoverByType(currency, convertToDate(currentDate), EntryType.CR));
            currentDayForexRecalculation.setClosingBalance(calculateClosingBalance(currentDayForexRecalculation));
            currentDayForexRecalculation.setProfitLossInUah(calculateProfitLoss(currentDayForexRecalculation, previousDayForexRecalculation));

            dailyRecalculations.add(currentDayForexRecalculation);
            previousDayForexRecalculation = currentDayForexRecalculation;
            currentDate = currentDate.plusDays(1);

        } while(currentDate.getYear() == currentYear);
        return dailyRecalculations;
    }

    private Date convertToDate(LocalDate currentDate) {
        return Date.from(currentDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }


    public BigDecimal calculateProfitLoss(ForexDailyRecalculation line, ForexDailyRecalculation previousLine) {
        if (previousLine.getCurrencyRate() == null)
            return BigDecimal.valueOf(0);
        BigDecimal currencyDiff = line.getCurrencyRate().subtract(previousLine.getCurrencyRate());
        BigDecimal changeInBalance = line.getClosingBalance().subtract(line.getOpeningBalance());
        return currencyDiff.multiply(changeInBalance);
    }

    public BigDecimal calculateClosingBalance(ForexDailyRecalculation line) {

        BigDecimal ob = line.getOpeningBalance();
        BigDecimal dr = line.getDailyDebitTurnover();
        BigDecimal cr = line.getDailyCreditTurnover();
        BigDecimal result = (ob).add(dr).add(cr);
        return result;
    }


    public BigDecimal calculateTurnoverByType(String currency, Date date, EntryType entryType) {
        return BigDecimal.valueOf(journalEntries.stream()
                .filter(c -> c.getCurrency().equals(currency))
                .filter(d -> d.getRecordDate().equals(date))
                .filter(type -> type.getEntryType().equals(entryType.toString()))
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

    public BigDecimal calculateOpeningBalance(String currency, ForexDailyRecalculation previousLine) throws NoSuchElementException {
        BigDecimal closingBalance;
        if (previousLine.getClosingBalance() == null) {closingBalance = extractOpeningBalanceFromTrialBalance(currency);
        } else { closingBalance = previousLine.getClosingBalance();}
        return closingBalance;
    }

    public BigDecimal extractOpeningBalanceFromTrialBalance(String currency) throws NoSuchElementException {
        TBAccount trialBalanceAccount = accountsTb.stream()
                .filter(c -> c.getCurrency().equals(currency))
                .findAny()
                .get();
        checkIfNullObjectReturned(trialBalanceAccount);
        return trialBalanceAccount.getOpeningBalance();
    }

    private void checkIfNullObjectReturned(TBAccount trialBalanceAccount) throws NoSuchElementException {
        if (trialBalanceAccount == null){
            throw new NoSuchElementException();
        }
    }
}
