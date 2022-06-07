package Forex.Main;

import Forex.CalculationLogic.ForexRecalculation;
import Forex.DataContainers.ForexDailyRecalculation;
import Forex.DataContainers.JERecord;
import Forex.DataContainers.NBURate;
import Forex.DataContainers.TBAccount;
import Forex.ExcelReader.ExcelReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {


        String INPUT_PATH = "D:\\git\\ForexCalculation\\.gitignore\\input.xlsx";
//
        ExcelReader excelReader = new ExcelReader(INPUT_PATH);
        ArrayList<JERecord> journalEntries = excelReader.readJournalEntries();
        ArrayList<TBAccount> accountsBalances = excelReader.readTrialBalance();
        ArrayList<NBURate> nbuRates = excelReader.readNbuRates();
////
////        Reconciliation reconciliation = new Reconciliation();
////        ArrayList<AccountReconciliation> accountReconciliations = reconciliation.performReconciliation(journalEntries, accountsBalances);
//
        ForexRecalculation forexDailyRecalculation = new ForexRecalculation(nbuRates, accountsBalances, journalEntries);
        List<Date> dates = forexDailyRecalculation.extractDates();
//        for (Date it: dates) {
//            System.out.println(it);
//        }
//
        List<ForexDailyRecalculation> forexDailyEmpty = forexDailyRecalculation.createEmptyDailyForex(dates);
        forexDailyEmpty.stream().forEach(System.out::println);
        List<ForexDailyRecalculation> forexDailyRecalculations = forexDailyRecalculation.performDailyRecalculationForCurrency("USD", forexDailyEmpty);
        forexDailyRecalculations.stream().forEach(System.out::println);



    }


}
