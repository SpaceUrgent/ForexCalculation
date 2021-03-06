package Forex.ExcelReader;

import Forex.DataContainers.JEHeader;
import Forex.DataContainers.JERecord;
import Forex.DataContainers.NBURate;
import Forex.DataContainers.TBAccount;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ExcelReader {

    String INPUT_PATH;

    public ArrayList<JERecord> readJournalEntries() throws IOException {

        FileInputStream inputStream = new FileInputStream(INPUT_PATH);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet journalEntriesSheet = workbook.getSheet("JE");

        ArrayList<JERecord> journalEntries = new ArrayList<>();

        Iterator<Row> rowIterator = journalEntriesSheet.iterator();
        Row headerRow = rowIterator.next();
//        JEHeader jeHeader = readJeHeader(headerRow);

        while (rowIterator.hasNext()){

            Row row = rowIterator.next();
//            Iterator<Cell> cellIterator = row.iterator();
//
//            Date recordDate = row.getCell(0).getDateCellValue();
//            String currency = row.getCell(1).getStringCellValue();
//            String entryType = row.getCell(2).getStringCellValue();
//            BigDecimal amount = BigDecimal.valueOf(row.getCell(3).getNumericCellValue());
            journalEntries.add(readRecord(row));

        }

//        System.out.println("RESULT");
//        System.out.println("_______________________________________");
//
//        for (JERecord it: journalEntries) {
//            System.out.println(it);
//        }
        return journalEntries;
    }

    public ExcelReader(String INPUT_PATH){
        this.INPUT_PATH = INPUT_PATH;
    }

    public JERecord readRecord(Row row){
        Date recordDate = row.getCell(0).getDateCellValue();
        String currency = row.getCell(1).getStringCellValue();
        String entryType = row.getCell(2).getStringCellValue();
        BigDecimal amount = BigDecimal.valueOf(row.getCell(3).getNumericCellValue());
        String accountId = new DataFormatter().formatCellValue(row.getCell(4));
        return new JERecord(recordDate, currency, entryType, amount, accountId);
    }

    public JEHeader readJeHeader(Row headerRow) {
        String date = headerRow.getCell(0).getStringCellValue();
        String currency = headerRow.getCell(1).getStringCellValue();
        String entry = headerRow.getCell(2).getStringCellValue();
        String amount = headerRow.getCell(3).getStringCellValue();
        return new JEHeader(date, currency, entry, amount);
    }

    public ArrayList<TBAccount> readTrialBalance() throws IOException {

        FileInputStream inputStream = new FileInputStream(INPUT_PATH);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet trialBalanceSheet = workbook.getSheet("TB");

        ArrayList<TBAccount> accountBalances = new ArrayList<>();

        Iterator<Row> rowIterator = trialBalanceSheet.iterator();
        Row headerRow = rowIterator.next();

        while (rowIterator.hasNext()){

            Row row = rowIterator.next();

            accountBalances.add(readAccountLine(row));

        }

//        System.out.println("RESULT");
//        System.out.println("_______________________________________");
//
//        for (TBAccount it: accountBalances) {
//            System.out.println(it);
//        }
        return accountBalances;
    }

    public ArrayList<NBURate> readNbuRates() throws IOException {

        FileInputStream inputStream = new FileInputStream(INPUT_PATH);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet nbuSheet = workbook.getSheet("NBU");

        ArrayList<NBURate> nbuRates = new ArrayList<>();

        Iterator<Row> rowIterator = nbuSheet.iterator();
        Row headerRow = rowIterator.next();

        while (rowIterator.hasNext()){

            Row row = rowIterator.next();

            nbuRates.add(readNbuRate(row));

        }

//        System.out.println("RESULT");
//        System.out.println("_______________________________________");
//
//        for (NBURate it: nbuRates) {
//            System.out.println(it);
//        }
        return nbuRates;
    }

    private NBURate readNbuRate(Row row) {
        Date date = row.getCell(0).getDateCellValue();
        String time = new DataFormatter().formatCellValue(row.getCell(1));
        String currencyCode = new DataFormatter().formatCellValue(row.getCell(2));
        String currency = new DataFormatter().formatCellValue(row.getCell(3));
        String currencyAmount = new DataFormatter().formatCellValue(row.getCell(4));
        String currencyUkr = new DataFormatter().formatCellValue(row.getCell(5));
        BigDecimal currencyRate = BigDecimal.valueOf(row.getCell(6).getNumericCellValue());
        return new NBURate(date, time, currencyCode, currency, currencyAmount, currencyUkr, currencyRate);
    }

    private TBAccount readAccountLine(Row row) {
        String accountId = new DataFormatter().formatCellValue(row.getCell(0));
        String accountDescription = new DataFormatter().formatCellValue(row.getCell(1));
        String currency = new DataFormatter().formatCellValue(row.getCell(2));
        String currencyCode = new DataFormatter().formatCellValue(row.getCell(3));
        BigDecimal openingBalance = BigDecimal.valueOf(row.getCell(4).getNumericCellValue());
        BigDecimal debitTurnover = BigDecimal.valueOf(row.getCell(5).getNumericCellValue());
        BigDecimal creditTurnover = BigDecimal.valueOf(row.getCell(6).getNumericCellValue());
        BigDecimal closingBalance = BigDecimal.valueOf(row.getCell(7).getNumericCellValue());
        return new TBAccount(accountId, accountDescription, currency, currencyCode,
                openingBalance, debitTurnover, creditTurnover, closingBalance);

    }


}
