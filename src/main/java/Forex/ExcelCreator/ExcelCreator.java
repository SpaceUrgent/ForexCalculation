package Forex.ExcelCreator;

import Forex.DataContainers.AccountReconciliation;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.*;
import org.apache.xmlbeans.impl.common.SAXHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;


public class ExcelCreator {
    String PATH_OUT;

    Workbook forexFile;

    Sheet forexSheet;

    public void createForexFile() throws FileNotFoundException {
        forexFile = new XSSFWorkbook();
        forexSheet = forexFile.createSheet("FOREX");

        Row firstRow = forexSheet.createRow(0);
        //Create first Cell
        Cell firstTableNumber = firstRow.createCell(0);

        firstTableNumber.setCellValue("Table 1");

        CellStyle tableNumberStyle = forexFile.createCellStyle();
        Font tableNumberFont = forexFile.createFont();
        tableNumberFont.setFontName("Arial");
        tableNumberFont.setFontHeightInPoints((short) 8);
        tableNumberFont.setBold(true);
        tableNumberFont.setColor(IndexedColors.DARK_BLUE.getIndex());
//        tableNumberFont.setColor(new XSSFColor(new java.awt.Color(48, 84, 150), new DefaultIndexedColorMap()).getIndex());
        tableNumberStyle.setFont(tableNumberFont);
        tableNumberStyle.setAlignment(HorizontalAlignment.RIGHT);
        tableNumberStyle.setFillPattern(FillPatternType.NO_FILL);
//        tableNumberStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(48, 84, 150), new DefaultIndexedColorMap()).getIndex());
//        tableNumberStyle.setFillForegroundColor(IndexedColors.BLUE1.getIndex());
        firstTableNumber.setCellStyle(tableNumberStyle);

        Cell tableName = firstRow.createCell(1);
        tableName.setCellValue("Reconciliation TB to JE");

        CellStyle tableNameStyle = forexFile.createCellStyle();
        Font tableNameFont = forexFile.createFont();
        tableNameFont.setFontName("Arial");
        tableNameFont.setFontHeightInPoints((short) 8);
        tableNameFont.setBold(true);
        tableNameStyle.setFont(tableNameFont);
        tableNameStyle.setAlignment(HorizontalAlignment.LEFT);
        tableName.setCellStyle(tableNameStyle);

        Row secondRow = forexSheet.createRow(1);

        CellStyle secondRowStyle = forexFile.createCellStyle();
        Font secondRowFont = forexFile.createFont();
        secondRowFont.setFontName("Arial");
        secondRowFont.setFontHeightInPoints((short) 8);
        secondRowFont.setItalic(true);
        secondRowStyle.setFont(secondRowFont);
        secondRowStyle.setBorderBottom(BorderStyle.MEDIUM);

        for (int i = 0; i < 12; i++) {

            Cell currentCell = secondRow.createCell(i);
            currentCell.setCellStyle(secondRowStyle);
            if (i == 4)
                currentCell.setCellValue("Per TB>>");
            if (i == 8)
                currentCell.setCellValue("Per JE>>");

        }

        CellStyle columnHeaderStyle = forexFile.createCellStyle();
        Font columnHeaderFont = forexFile.createFont();

        columnHeaderFont.setFontName("Arial");
        columnHeaderFont.setFontHeightInPoints((short) 8);
        columnHeaderFont.setBold(true);
        columnHeaderStyle.setFont(columnHeaderFont);
        columnHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        columnHeaderStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

        columnHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
        columnHeaderStyle.setBorderBottom(BorderStyle.MEDIUM);

        CellStyle columnHeaderStyleDiff = forexFile.createCellStyle();
        Font columnHeaderFontDiff = forexFile.createFont();

        columnHeaderFontDiff.setFontName("Arial");
        columnHeaderFontDiff.setFontHeightInPoints((short) 8);
        columnHeaderFontDiff.setBold(true);
        columnHeaderFontDiff.setColor(IndexedColors.RED.getIndex());
        columnHeaderStyleDiff.setFont(columnHeaderFontDiff);
        columnHeaderStyleDiff.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        columnHeaderStyleDiff.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//        columnHeaderStyleDiff.setFillBackgroundColor(IndexedColors.RED.getIndex());
        columnHeaderStyleDiff.setAlignment(HorizontalAlignment.CENTER);
        columnHeaderStyleDiff.setBorderBottom(BorderStyle.MEDIUM);

        Row thirdRow = forexSheet.createRow(2);

        String[] columnNames = {"Acc", "Descr", "Currency", "Curr/ Code", "OB", "DR TO", "CR TO", "CB", "DR TO", "CR TO", "Diff", "Diff"};

        for (int i = 0; i < 12; i++) {
            String columnHeader = columnNames[i];
            Cell currentCell = thirdRow.createCell(i);
            currentCell.setCellValue(columnHeader);
            if (i > 9) {
                currentCell.setCellStyle(columnHeaderStyleDiff);
            } else {
                currentCell.setCellStyle(columnHeaderStyle);
            }
        }

        try (OutputStream outputStream = new FileOutputStream(PATH_OUT)){
            forexFile.write(outputStream);
        } catch (Exception e) {
            throw new FileNotFoundException();
        }



    }

    public void fillReconciliationTable(List<AccountReconciliation> accountsReconciliation) throws IOException {
        long size = accountsReconciliation.stream().count();
        int currentRowIndex = 3;
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
        for (int i = 0; i < 12; i++) {
            Cell currentCell = reconciliationTableClosingRow.createCell(i);
            currentCell.setCellStyle(createClosingRowStyle());
        }

        try (OutputStream outputStream = new FileOutputStream(PATH_OUT)){
            forexFile.write(outputStream);
        } catch (Exception e) {
            throw new IOException();
        }
    }

    private CellStyle createClosingRowStyle() {
        CellStyle closingStyle = forexFile.createCellStyle();
        closingStyle.setBorderTop(BorderStyle.THIN);
        return closingStyle;
    }

    private void formatReconciliationTable(Row currentRow) {
        Iterator<Cell> cellIterator = currentRow.cellIterator();

        while (cellIterator.hasNext()){
            Cell currentCell = cellIterator.next();
            if (currentCell.getColumnIndex() < 4) {
                currentCell.setCellStyle(createMainStyle());
            } else if (currentCell.getColumnIndex() < 10) {
                currentCell.setCellStyle(createAccountingStyle());
            } else {
                currentCell.setCellStyle(createDiffStyle());
            }


        }
    }


    private CellStyle createDiffStyle() {
        CellStyle diffStyle = forexFile.createCellStyle();
        Font diffFont = forexFile.createFont();
        DataFormat diffFormat = forexFile.createDataFormat();

        diffFont.setFontName("Arial");
        diffFont.setFontHeightInPoints((short) 8);
        diffFont.setColor(IndexedColors.RED.getIndex());
        diffStyle.setDataFormat(diffFormat.getFormat("#,##0_);(#,##0)"));
        diffStyle.setFont(diffFont);
        diffStyle.setFillPattern(FillPatternType.NO_FILL);

        return diffStyle;
        
    }

    private CellStyle createAccountingStyle() {
        CellStyle accountingStyle = forexFile.createCellStyle();
        Font accountingFont = forexFile.createFont();
        DataFormat accountingFormat = forexFile.createDataFormat();

        accountingFont.setFontName("Arial");
        accountingFont.setFontHeightInPoints((short) 8);
        accountingStyle.setDataFormat(accountingFormat.getFormat("#,##0_);(#,##0)"));
        accountingStyle.setFont(accountingFont);
        
        return accountingStyle;
    }

    private CellStyle createMainStyle() {
        CellStyle mainStyle = forexFile.createCellStyle();
        Font mainFont = forexFile.createFont();

        mainFont.setFontName("Arial");
        mainFont.setFontHeightInPoints((short) 8);
        mainStyle.setFont(mainFont);
        return mainStyle;
    }

    public ExcelCreator(String PATH_OUT) {
        this.PATH_OUT = PATH_OUT;
    }
}
