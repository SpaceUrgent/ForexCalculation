package Forex.Main;

import Forex.DataContainers.JERecord;
import Forex.DataContainers.TBAccount;
import Forex.ExcelReader.ExcelReader;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {


        String INPUT_PATH = "D:\\git\\ForexCalculation\\.gitignore\\input.xlsx";

        ExcelReader excelReader = new ExcelReader(INPUT_PATH);
        ArrayList<JERecord> journalEntries = excelReader.readJournalEntries();
        ArrayList<TBAccount> accountsBalances = excelReader.readTrialBalance();


    }


}
