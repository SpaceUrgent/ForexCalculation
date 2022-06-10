package Forex.ExcelCreator;

import Forex.DataContainers.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static Forex.Consants.Constants.*;



public class ExcelCreator {
    String PATH_OUT;

    Workbook forexFile;

    Sheet forexSheet;


    public void createForexFile(List<AccountReconciliation> accountsReconciliations, List<TotalProfitLossForCurrency> profitAndLossTotals, List<ForexDailyRecalculation> forexDailyRecalculations, List<DailyRecalculationReconciliation> dailyRecalculationReconciliationsToTrialBalance) throws IOException {
        forexFile = new XSSFWorkbook();
        forexSheet = forexFile.createSheet("FOREX");

        createRowsBeforeReconciliationTable();
        int currentRowIndex = fillReconciliationTable(accountsReconciliations);
        currentRowIndex = createDelimiterRows(currentRowIndex);
        currentRowIndex++;

        currentRowIndex = createTotalProfitAndLossRow(currentRowIndex, profitAndLossTotals);
        createForexTables(currentRowIndex, profitAndLossTotals, forexDailyRecalculations, dailyRecalculationReconciliationsToTrialBalance);

        try (OutputStream outputStream = new FileOutputStream(PATH_OUT)){
            forexFile.write(outputStream);
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }

    public void createRowsBeforeReconciliationTable(){
        Row firstRow = forexSheet.createRow(0);

        Cell firstTableNumber = firstRow.createCell(0);

        firstTableNumber.setCellValue("Table 1");
        firstTableNumber.setCellStyle(StyleHelper.createTableNumberStyle(forexFile));

        Cell tableName = firstRow.createCell(1);
        tableName.setCellValue("Reconciliation TB to JE");

        Row secondRow = forexSheet.createRow(1);

        for (int i = 0; i < RECONCILIATION_TABLE_LENGTH; i++) {

            Cell currentCell = secondRow.createCell(i);
            currentCell.setCellStyle(StyleHelper.createTableHeaderAnnotationStyle(forexFile));
            if (i == 4)
                currentCell.setCellValue("Per TB>>");
            if (i == 8)
                currentCell.setCellValue("Per JE>>");

        }

        Row thirdRow = forexSheet.createRow(2);

        for (int i = 0; i < RECONCILIATION_TABLE_LENGTH; i++) {

            String currentColumnHeader = RECONCILIATION_TABLE_COLUMN_NAMES[i];
            Cell currentCell = thirdRow.createCell(i);
            currentCell.setCellValue(currentColumnHeader);
            if (i > 9) {
                currentCell.setCellStyle(StyleHelper.createColumnDifferenceHeaderStyle(forexFile));
            } else {
                currentCell.setCellStyle(StyleHelper.createColumnHeaderStyle(forexFile));
            }
        }

    }

    public int fillReconciliationTable(List<AccountReconciliation> accountsReconciliation) throws IOException {
        long size = accountsReconciliation.stream().count();
        int currentRowIndex = RECONCILIATION_DATA_FIRST_ROW;
        for (AccountReconciliation iterator: accountsReconciliation) {
             Row currentRow = forexSheet.createRow(currentRowIndex);

             Cell accId = currentRow.createCell(0);
             accId.setCellValue(iterator.getAccountId());
             Cell accDescr = currentRow.createCell(1);
             accDescr.setCellValue(iterator.getAccountDescription());
             Cell currency = currentRow.createCell(2);
             currency.setCellValue(iterator.getAccountCurrency());
             Cell currencyCode = currentRow.createCell(3);
             currencyCode.setCellValue(iterator.getCurrencyCode());
             Cell openingBalance = currentRow.createCell(4);
             openingBalance.setCellValue(iterator.getOpeningBalancePerTb().doubleValue());
             Cell debitTurnoverPerTb = currentRow.createCell(5);
             debitTurnoverPerTb.setCellValue(iterator.getDebitTurnoverPerTb().doubleValue());
             Cell creditTurnoverPerTb = currentRow.createCell(6);
             creditTurnoverPerTb.setCellValue(iterator.getCreditTurnoverPerTb().doubleValue());
             Cell closingBalance = currentRow.createCell(7);
             closingBalance.setCellValue(iterator.getClosingBalancePerTb().doubleValue());
             Cell debitTurnoverPerJe = currentRow.createCell(8);
             debitTurnoverPerJe.setCellValue(iterator.getDebitTurnoverPerJe().doubleValue());
             Cell creditTurnoverPerJe = currentRow.createCell(9);
             creditTurnoverPerJe.setCellValue(iterator.getCreditTurnoverPerJe().doubleValue());
             Cell debitTurnoverDiff = currentRow.createCell(10);
             debitTurnoverDiff.setCellValue(iterator.getDebitTurnoverDifference().doubleValue());
             Cell creditTurnoverDiff = currentRow.createCell(11);
             creditTurnoverDiff.setCellValue(iterator.getCreditTurnoverDifference().doubleValue());

             formatReconciliationTable(currentRow);

             currentRowIndex++;
        }

        Row reconciliationTableClosingRow = forexSheet.createRow(currentRowIndex);
        for (int i = 0; i < RECONCILIATION_TABLE_LENGTH; i++) {
            Cell currentCell = reconciliationTableClosingRow.createCell(i);
            currentCell.setCellStyle(StyleHelper.createClosingRowStyle(forexFile));
        }

        try (OutputStream outputStream = new FileOutputStream(PATH_OUT)){
            forexFile.write(outputStream);
        } catch (Exception e) {
            throw new IOException();
        }

        return currentRowIndex;
    }



    private void formatReconciliationTable(Row currentRow) {
        Iterator<Cell> cellIterator = currentRow.cellIterator();

        while (cellIterator.hasNext()){
            Cell currentCell = cellIterator.next();
            if (currentCell.getColumnIndex() < 4) {
                currentCell.setCellStyle(StyleHelper.createMainDataStyle(forexFile));
            } else if (currentCell.getColumnIndex() < 10) {
                currentCell.setCellStyle(StyleHelper.createAccountingStyle(forexFile));
            } else {
                currentCell.setCellStyle(StyleHelper.createDifferencesStyle(forexFile));
            }
        }
    }

    public int createDelimiterRows(int currentRowIndex){

        Row delimiterRow = forexSheet.createRow(currentRowIndex + 2);

        for (int i = 0; i < 100; i++) {
            Cell currentCell = delimiterRow.createCell(i);
            currentCell.setCellStyle(StyleHelper.createDelimiterRowStyle(forexFile));
            if (i == 0) currentCell.setCellValue("Forex recalculation for Cash accounts");
        }

        currentRowIndex += 3;
        return currentRowIndex;
    }

    public int createTotalProfitAndLossRow(int currentRowIndex, List<TotalProfitLossForCurrency> profitAndLostTotals){

        Row totalForesProfitAndLossRow = forexSheet.createRow(currentRowIndex);
        Cell firstNamingCell = totalForesProfitAndLossRow.createCell(1);
        Cell secondNamingCell = totalForesProfitAndLossRow.createCell(2);
        Cell amountCell = totalForesProfitAndLossRow.createCell(3);

        firstNamingCell.setCellValue("Total Forex P/L");
        firstNamingCell.setCellStyle(StyleHelper.createLeftBorderedCellStyle(forexFile));
        secondNamingCell.setCellStyle(StyleHelper.createLeftBorderedCellStyle(forexFile));
        forexSheet.addMergedRegion(new CellRangeAddress(currentRowIndex, currentRowIndex, 1, 2));

        amountCell.setCellValue(TotalProfitLoss.calculateTotalProfitAndLost(profitAndLostTotals).doubleValue());
        amountCell.setCellStyle(StyleHelper.createRightBorderedCellStyle(forexFile));

        currentRowIndex += 2;
        return currentRowIndex;
    }

    public void createForexTables(int currentRowIndex, List<TotalProfitLossForCurrency> profitLossTotals, List<ForexDailyRecalculation> forexDailyRecalculations, List<DailyRecalculationReconciliation> dailyRecalculationReconciliationsToTrialBalance){

        List<String> uniqueCurrencies = forexDailyRecalculations.stream()
                .map(ForexDailyRecalculation::getCurrency)
                .distinct()
                .collect(Collectors.toList());

        currentRowIndex = createTablesNumbersAndNames(currentRowIndex, uniqueCurrencies);
        currentRowIndex = createTotalProfitLossForEachCurrency(currentRowIndex, uniqueCurrencies, profitLossTotals);
        currentRowIndex = createReconciliationsForForexRecalculations(currentRowIndex, uniqueCurrencies, dailyRecalculationReconciliationsToTrialBalance);
        currentRowIndex = createForexDailyRecalculations(currentRowIndex, uniqueCurrencies, forexDailyRecalculations);

    }

    private int createForexDailyRecalculations(int currentRowIndex, List<String> uniqueCurrencies, List<ForexDailyRecalculation> forexDailyRecalculations) {
        currentRowIndex = createForexDailyCalculationsHeaderRow(currentRowIndex, uniqueCurrencies);
        currentRowIndex = fillForexDailyCalculationTables(currentRowIndex, uniqueCurrencies, forexDailyRecalculations);
        return  currentRowIndex;
    }

    private int fillForexDailyCalculationTables(int currentRowIndex, List<String> uniqueCurrencies, List<ForexDailyRecalculation> forexDailyRecalculations) {

        List<Date> datesInYear = extractDates(forexDailyRecalculations);
        for (Date currentDate: datesInYear) {
            currentRowIndex = fillCalculationRowForCurrentDate(currentDate, currentRowIndex, uniqueCurrencies, forexDailyRecalculations);
        }
            currentRowIndex = createClosingLineForCalculationTables(currentRowIndex, uniqueCurrencies);
        return ++currentRowIndex;
    }

    private int createClosingLineForCalculationTables(int currentRowIndex, List<String> uniqueCurrencies) {
        int beginningColumn = BEGINNING_COLUMN_INDEX;
        Row closingLineRow = forexSheet.createRow(currentRowIndex);
        for (String currentCurrency: uniqueCurrencies) {
            for (int i = 0; i < FOREX_DAILY_TABLE_LENGTH; i++) {
                Cell currentCell = closingLineRow.createCell(beginningColumn + i);
                currentCell.setCellStyle(StyleHelper.createClosingRowStyle(forexFile));
            }
            beginningColumn += 9;
        }
        return ++currentRowIndex;
    }

    private int fillCalculationRowForCurrentDate(Date currentDate, int currentRowIndex, List<String> uniqueCurrencies, List<ForexDailyRecalculation> forexDailyRecalculations) {
        int beginningColumn = BEGINNING_COLUMN_INDEX;
        Row currentDateRow = forexSheet.createRow(currentRowIndex);
        for (String currentCurrency: uniqueCurrencies) {
            ForexDailyRecalculation currentDateAndCurrencyCalculation = findDailyCalculationByDateCurrency(currentDate, currentCurrency, forexDailyRecalculations);

            Cell dateCell = currentDateRow.createCell(beginningColumn);
            dateCell.setCellValue(currentDateAndCurrencyCalculation.getDate());
            dateCell.setCellStyle(StyleHelper.createDateCellStyle(forexFile));
            Cell currencyRateCell = currentDateRow.createCell(beginningColumn + 1);
            currencyRateCell.setCellValue(currentDateAndCurrencyCalculation.getCurrencyRate().doubleValue());
            currencyRateCell.setCellStyle(StyleHelper.createCurrencyRateStyle(forexFile));
            Cell openingBalanceCell = currentDateRow.createCell(beginningColumn + 2);
            openingBalanceCell.setCellValue(currentDateAndCurrencyCalculation.getOpeningBalance().doubleValue());
            openingBalanceCell.setCellStyle(StyleHelper.createAccountingStyle(forexFile));
            Cell debitTurnoverCell = currentDateRow.createCell(beginningColumn + 3);
            debitTurnoverCell.setCellValue(currentDateAndCurrencyCalculation.getDailyDebitTurnover().doubleValue());
            debitTurnoverCell.setCellStyle(StyleHelper.createAccountingStyle(forexFile));
            Cell creditTurnoverCell = currentDateRow.createCell(beginningColumn + 4);
            creditTurnoverCell.setCellValue(currentDateAndCurrencyCalculation.getDailyCreditTurnover().doubleValue());
            creditTurnoverCell.setCellStyle(StyleHelper.createAccountingStyle(forexFile));
            Cell closingBalanceCell = currentDateRow.createCell(beginningColumn + 5);
            closingBalanceCell.setCellValue(currentDateAndCurrencyCalculation.getClosingBalance().doubleValue());
            closingBalanceCell.setCellStyle(StyleHelper.createAccountingStyle(forexFile));
            Cell profitLossCell = currentDateRow.createCell(beginningColumn+6);
            profitLossCell.setCellValue(currentDateAndCurrencyCalculation.getProfitLossInUah().doubleValue());
            profitLossCell.setCellStyle(StyleHelper.createAccountingStyle(forexFile));

            beginningColumn += 9;
        }
        return ++currentRowIndex;
    }

    private ForexDailyRecalculation findDailyCalculationByDateCurrency(Date currentDate, String currentCurrency, List<ForexDailyRecalculation> forexDailyRecalculations) {
        return forexDailyRecalculations.stream()
                .filter(dailyRecalculation -> dailyRecalculation.getDate().equals(currentDate))
                .filter(dailyRecalculation -> dailyRecalculation.getCurrency().equals(currentCurrency))
                .findFirst()
                .get();
    }

    private List<Date> extractDates(List<ForexDailyRecalculation> forexDailyRecalculations) {
        return forexDailyRecalculations.stream()
                .map(ForexDailyRecalculation::getDate)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    private int createForexDailyCalculationsHeaderRow(int currentRowIndex, List<String> uniqueCurrencies) {
        int beginningColumn = BEGINNING_COLUMN_INDEX;
        Row forexDailyRecalculationsHeaderRow = forexSheet.createRow(currentRowIndex);
        for (String currentCurrency: uniqueCurrencies) {
            for (int i = 0; i < FOREX_DAILY_TABLE_LENGTH; i++) {
                Cell currentCell = forexDailyRecalculationsHeaderRow.createCell(i + beginningColumn);
                currentCell.setCellValue(FOREX_DAILY_TABLE_COLUMN_NAMES[i]);
                currentCell.setCellStyle(StyleHelper.createColumnHeaderStyle(forexFile));
            }
            beginningColumn += 9;
        }
        return ++currentRowIndex;
    }

    private int createReconciliationsForForexRecalculations(int currentRowIndex, List<String> uniqueCurrencies, List<DailyRecalculationReconciliation> dailyRecalculationReconciliationsToTrialBalance) {

        currentRowIndex = createReconciliationHeaderRow(currentRowIndex, uniqueCurrencies.size());
        currentRowIndex = createReconciliationPerCalculationRow(currentRowIndex, uniqueCurrencies, PER_CALCULATION_LINE_TYPE, dailyRecalculationReconciliationsToTrialBalance);
        currentRowIndex = createReconciliationPerCalculationRow(currentRowIndex, uniqueCurrencies, PER_TRIAL_BALANCE_LINE_TYPE,dailyRecalculationReconciliationsToTrialBalance);
        currentRowIndex = createCheckForDifferencesRow(currentRowIndex, uniqueCurrencies, dailyRecalculationReconciliationsToTrialBalance);

        return ++currentRowIndex;
    }

    private int createCheckForDifferencesRow(int currentRowIndex, List<String> uniqueCurrencies, List<DailyRecalculationReconciliation> dailyRecalculationReconciliationsToTrialBalance) {
        int beginningColumn = BEGINNING_COLUMN_INDEX + 1;
        Row reconciliationPerCalculationRow = forexSheet.createRow(currentRowIndex);
        List<DailyRecalculationReconciliation> turnoverAmountsPerCalculation = extractTurnoverAmountsPerLineType(CHECK_LINE_TYPE, dailyRecalculationReconciliationsToTrialBalance);

        for (String currentCurrency: uniqueCurrencies) {
            DailyRecalculationReconciliation dailyRecalculationReconciliationForCurrentCurrency = dailyRecalculationReconciliationForCurrentCurrency(currentCurrency, turnoverAmountsPerCalculation);

            Cell descriptionCell = reconciliationPerCalculationRow.createCell(beginningColumn);
            descriptionCell.setCellValue(dailyRecalculationReconciliationForCurrentCurrency.getDescription());
            descriptionCell.setCellStyle(StyleHelper.createCheckLineCellStyle(forexFile));
            Cell openingBalanceCell = reconciliationPerCalculationRow.createCell(beginningColumn + 1);
            openingBalanceCell.setCellValue(dailyRecalculationReconciliationForCurrentCurrency.getOpeningBalance().doubleValue());
            openingBalanceCell.setCellStyle(StyleHelper.createCheckLineCellStyle(forexFile));
            Cell debitTurnoverCell = reconciliationPerCalculationRow.createCell(beginningColumn + 2);
            debitTurnoverCell.setCellStyle(StyleHelper.createCheckLineCellStyle(forexFile));
            debitTurnoverCell.setCellValue(dailyRecalculationReconciliationForCurrentCurrency.getDebitTurnover().doubleValue());
            Cell creditTurnoverCell = reconciliationPerCalculationRow.createCell(beginningColumn + 3);
            creditTurnoverCell.setCellStyle(StyleHelper.createCheckLineCellStyle(forexFile));
            creditTurnoverCell.setCellValue(dailyRecalculationReconciliationForCurrentCurrency.getCreditTurnover().doubleValue());
            Cell closingBalanceCell = reconciliationPerCalculationRow.createCell(beginningColumn + 4);
            closingBalanceCell.setCellValue(dailyRecalculationReconciliationForCurrentCurrency.getClosingBalance().doubleValue());
            closingBalanceCell.setCellStyle(StyleHelper.createCheckLineCellStyle(forexFile));
            beginningColumn += 9;
        }
        return ++currentRowIndex;
    }

    private int createReconciliationPerCalculationRow(int currentRowIndex, List<String> uniqueCurrencies, String lineType, List<DailyRecalculationReconciliation> dailyRecalculationReconciliationsToTrialBalance) {
        int beginningColumn = BEGINNING_COLUMN_INDEX + 1;
        Row reconciliationPerCalculationRow = forexSheet.createRow(currentRowIndex);
        List<DailyRecalculationReconciliation> turnoverAmountsPerCalculation = extractTurnoverAmountsPerLineType(lineType, dailyRecalculationReconciliationsToTrialBalance);

        for (String currentCurrency: uniqueCurrencies) {
            DailyRecalculationReconciliation dailyRecalculationReconciliationForCurrentCurrency = dailyRecalculationReconciliationForCurrentCurrency(currentCurrency, turnoverAmountsPerCalculation);

            Cell descriptionCell = reconciliationPerCalculationRow.createCell(beginningColumn);
            descriptionCell.setCellValue(dailyRecalculationReconciliationForCurrentCurrency.getDescription());
            descriptionCell.setCellStyle(StyleHelper.createMainDataStyle(forexFile));
            Cell openingBalanceCell = reconciliationPerCalculationRow.createCell(beginningColumn + 1);
            openingBalanceCell.setCellValue(dailyRecalculationReconciliationForCurrentCurrency.getOpeningBalance().doubleValue());
            openingBalanceCell.setCellStyle(StyleHelper.createAccountingStyle(forexFile));
            Cell debitTurnoverCell = reconciliationPerCalculationRow.createCell(beginningColumn + 2);
            debitTurnoverCell.setCellStyle(StyleHelper.createAccountingStyle(forexFile));
            debitTurnoverCell.setCellValue(dailyRecalculationReconciliationForCurrentCurrency.getDebitTurnover().doubleValue());
            Cell creditTurnoverCell = reconciliationPerCalculationRow.createCell(beginningColumn + 3);
            creditTurnoverCell.setCellStyle(StyleHelper.createAccountingStyle(forexFile));
            creditTurnoverCell.setCellValue(dailyRecalculationReconciliationForCurrentCurrency.getCreditTurnover().doubleValue());
            Cell closingBalanceCell = reconciliationPerCalculationRow.createCell(beginningColumn + 4);
            closingBalanceCell.setCellValue(dailyRecalculationReconciliationForCurrentCurrency.getClosingBalance().doubleValue());
            closingBalanceCell.setCellStyle(StyleHelper.createAccountingStyle(forexFile));
            beginningColumn = beginningColumn + 9;
        }
        return ++currentRowIndex;
    }

    private DailyRecalculationReconciliation dailyRecalculationReconciliationForCurrentCurrency(String currentCurrency, List<DailyRecalculationReconciliation> turnoverAmountsPerCalculation) {
        return turnoverAmountsPerCalculation.stream()
                .filter(perRecalculationTotals -> perRecalculationTotals.getCurrency().equals(currentCurrency))
                .findFirst()
                .get();
    }

    private List<DailyRecalculationReconciliation> extractTurnoverAmountsPerLineType(String reconciliationLineType , List<DailyRecalculationReconciliation> dailyRecalculationReconciliationsToTrialBalance) {
        return dailyRecalculationReconciliationsToTrialBalance.stream()
                .filter(reconciliation -> reconciliation.getDescription().equals(reconciliationLineType))
                .collect(Collectors.toList());
    }


    private int createReconciliationHeaderRow(int currentRowIndex, int size) {
        int beginningColumn = BEGINNING_COLUMN_INDEX + 1;
        Row reconciliationHeaderRow = forexSheet.createRow(currentRowIndex);

        for (int i = 0; i < size; i++) {
            createHeaderCells(reconciliationHeaderRow, beginningColumn);
            beginningColumn += 9;
        }

        return ++currentRowIndex;
    }

    private void createHeaderCells(Row reconciliationHeaderRow, int beginningColumn) {
        for (int i = 0; i < CALCULATION_RECONCILIATION_LENGTH; i++) {
            Cell currentHeaderCell = reconciliationHeaderRow.createCell(beginningColumn);
            currentHeaderCell.setCellValue(CALCULATION_RECONCILIATION_COLUMN_NAMES[i]);
            currentHeaderCell.setCellStyle(StyleHelper.createColumnHeaderStyle(forexFile));
            beginningColumn++;
        }
    }

    private int createTotalProfitLossForEachCurrency(int currentRowIndex, List<String> uniqueCurrencies, List<TotalProfitLossForCurrency> profitLossTotals) {
        Row totalProfitLossForEachCurrencyRow = forexSheet.createRow(currentRowIndex);
        int beginningColumn = BEGINNING_COLUMN_INDEX + 1;

        for (String currentCurrency : uniqueCurrencies) {
            Cell titleCell = totalProfitLossForEachCurrencyRow.createCell(beginningColumn);
            titleCell.setCellValue("Forex P/L");
            titleCell.setCellStyle(StyleHelper.createLeftBorderedCellStyle(forexFile));

            Cell amountCell = totalProfitLossForEachCurrencyRow.createCell(beginningColumn+1);
            amountCell.setCellValue(extractProfitLossByCurrency(currentCurrency, profitLossTotals));
            amountCell.setCellStyle(StyleHelper.createRightBorderedCellStyle(forexFile));
            beginningColumn += 9;

        }
        currentRowIndex += 2;
        return currentRowIndex;
    }

    private double extractProfitLossByCurrency(String currentCurrency, List<TotalProfitLossForCurrency> profitLossTotals) {
        return profitLossTotals.stream()
                .filter(profitLossTotal -> profitLossTotal.getCurrency().equals(currentCurrency))
                .findAny()
                .get()
                .getTotalProfitLoss().doubleValue();
    }



    private int createTablesNumbersAndNames(int currentRowIndex, List<String> uniqueCurrencies) {
        Row tablesNumbersAndNamesRow = forexSheet.createRow(currentRowIndex);

        int beginningColumn = BEGINNING_COLUMN_INDEX;
        int tableIndex = 1;
        for (String currentCurrency: uniqueCurrencies) {
            Cell tableNumberCell = tablesNumbersAndNamesRow.createCell(beginningColumn);
            tableNumberCell.setCellValue("Table 2." + tableIndex);
            tableNumberCell.setCellStyle(StyleHelper.createTableNumberStyle(forexFile));

            Cell tableNameCell = tablesNumbersAndNamesRow.createCell(beginningColumn+1);
            tableNameCell.setCellValue(currentCurrency);
            tableNameCell.setCellStyle(StyleHelper.createTableNameStyle(forexFile));

            beginningColumn += 9;
        }
        currentRowIndex += 2;
        return currentRowIndex;
    }


    public ExcelCreator(String PATH_OUT) {
        this.PATH_OUT = PATH_OUT;
    }
}
