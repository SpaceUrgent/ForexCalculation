package Forex.Main;

import Forex.CalculationLogic.ForexRecalculation;
import Forex.CalculationLogic.Reconciliation;
import Forex.DataContainers.*;
import Forex.ExcelCreator.ExcelCreator;
import Forex.ExcelReader.ExcelReader;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {


        String INPUT_PATH = "D:\\git\\ForexCalculation\\.gitignore\\input.xlsx";

        ExcelReader excelReader = new ExcelReader(INPUT_PATH);
        ArrayList<JERecord> journalEntries = excelReader.readJournalEntries();
        ArrayList<TBAccount> accountsBalances = excelReader.readTrialBalance();
        ArrayList<NBURate> nbuRates = excelReader.readNbuRates();

        Reconciliation reconciliation = new Reconciliation();
        ArrayList<AccountReconciliation> accountReconciliations = reconciliation.performReconciliation(journalEntries, accountsBalances);

        ForexRecalculation forexDailyRecalculation = new ForexRecalculation(nbuRates, accountsBalances, journalEntries , 2021);

//        forexDailyEmpty.stream().forEach(System.out::println);
        List<ForexDailyRecalculation> forexDailyRecalculations = forexDailyRecalculation.performDailyRecalculationForAllCurrencies();
//        forexDailyRecalculations.stream().forEach(System.out::println);
        List<TotalProfitLossForCurrency> profitLossTotalsForEachCurrency = forexDailyRecalculation.calculateTotalProfitLoss(forexDailyRecalculations);
        List<DailyRecalculationReconciliation> dailyRecalculationReconciliationsToTrialBalance = forexDailyRecalculation.reconcileDailyCalculationToTrialBalance(forexDailyRecalculations);

        String pathOut = "C:\\Users\\Andrey\\Desktop\\output\\file.xlsx";

        ExcelCreator excelCreator = new ExcelCreator(pathOut);
        excelCreator.createForexFile(accountReconciliations, profitLossTotalsForEachCurrency, forexDailyRecalculations, dailyRecalculationReconciliationsToTrialBalance);




    }


}
