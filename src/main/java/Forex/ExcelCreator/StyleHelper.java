package Forex.ExcelCreator;

import org.apache.poi.ss.usermodel.*;

import static Forex.Consants.Constants.FONT;
import static Forex.Consants.Constants.FONT_SIZE;

public class StyleHelper {

    public static CellStyle createTableNumberStyle(Workbook workbook){

        CellStyle tableNumberStyle = workbook.createCellStyle();
        Font tableNumberFont = workbook.createFont();

        tableNumberFont.setFontName(FONT);
        tableNumberFont.setFontHeightInPoints(FONT_SIZE);
        tableNumberFont.setBold(true);
        tableNumberFont.setColor(IndexedColors.DARK_BLUE.getIndex());

        tableNumberStyle.setFont(tableNumberFont);
        tableNumberStyle.setAlignment(HorizontalAlignment.RIGHT);
        tableNumberStyle.setFillPattern(FillPatternType.NO_FILL);

        return tableNumberStyle;
    }

    public static CellStyle createTableNameStyle(Workbook workbook){
        CellStyle tableNameStyle = workbook.createCellStyle();
        Font tableNameFont = workbook.createFont();
        tableNameFont.setFontName(FONT);
        tableNameFont.setFontHeightInPoints(FONT_SIZE);
        tableNameFont.setBold(true);
        tableNameStyle.setFont(tableNameFont);
        tableNameStyle.setAlignment(HorizontalAlignment.LEFT);

        return tableNameStyle;
    }

    public static CellStyle createTableHeaderAnnotationStyle(Workbook workbook){
        CellStyle tableHeaderAnnotationStyle = workbook.createCellStyle();
        Font tableHeaderAnnotationFont = workbook.createFont();
        tableHeaderAnnotationFont.setFontName(FONT);
        tableHeaderAnnotationFont.setFontHeightInPoints(FONT_SIZE);
        tableHeaderAnnotationFont.setItalic(true);
        tableHeaderAnnotationStyle.setFont(tableHeaderAnnotationFont);
        tableHeaderAnnotationStyle.setBorderBottom(BorderStyle.MEDIUM);

        return tableHeaderAnnotationStyle;
    }

    public static CellStyle createColumnHeaderStyle(Workbook workbook){
        CellStyle columnHeaderStyle = workbook.createCellStyle();
        Font columnHeaderFont = workbook.createFont();

        columnHeaderFont.setFontName(FONT);
        columnHeaderFont.setFontHeightInPoints(FONT_SIZE);
        columnHeaderFont.setBold(true);
        columnHeaderStyle.setFont(columnHeaderFont);
        columnHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        columnHeaderStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        columnHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
        columnHeaderStyle.setBorderTop(BorderStyle.MEDIUM);
        columnHeaderStyle.setBorderBottom(BorderStyle.MEDIUM);

        return columnHeaderStyle;
    }

    public static CellStyle createColumnDifferenceHeaderStyle(Workbook workbook){
        CellStyle columnHeaderStyleDiff = workbook.createCellStyle();
        Font columnHeaderFontDiff = workbook.createFont();

        columnHeaderFontDiff.setFontName(FONT);
        columnHeaderFontDiff.setFontHeightInPoints(FONT_SIZE);
        columnHeaderFontDiff.setBold(true);
        columnHeaderFontDiff.setColor(IndexedColors.RED.getIndex());
        columnHeaderStyleDiff.setFont(columnHeaderFontDiff);
        columnHeaderStyleDiff.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        columnHeaderStyleDiff.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        columnHeaderStyleDiff.setAlignment(HorizontalAlignment.CENTER);
        columnHeaderStyleDiff.setBorderBottom(BorderStyle.MEDIUM);

        return columnHeaderStyleDiff;
    }

    public static CellStyle createMainDataStyle(Workbook workbook){
        CellStyle mainDataStyle = workbook.createCellStyle();
        Font mainDataFont = workbook.createFont();

        mainDataFont.setFontName(FONT);
        mainDataFont.setFontHeightInPoints(FONT_SIZE);
        mainDataStyle.setFont(mainDataFont);

        return mainDataStyle;
    }

    public static CellStyle createAccountingStyle(Workbook workbook){
        CellStyle accountingStyle = workbook.createCellStyle();
        Font accountingFont = workbook.createFont();
        DataFormat accountingFormat = workbook.createDataFormat();

        accountingFont.setFontName(FONT);
        accountingFont.setFontHeightInPoints(FONT_SIZE);
        accountingStyle.setDataFormat(createAccountingFormat(workbook));
        accountingStyle.setFont(accountingFont);

        return accountingStyle;
    }

    public static CellStyle createCurrencyRateStyle(Workbook workbook){
        CellStyle accountingStyle = workbook.createCellStyle();
        Font accountingFont = workbook.createFont();
        DataFormat accountingFormat = workbook.createDataFormat();

        accountingFont.setFontName(FONT);
        accountingFont.setFontHeightInPoints(FONT_SIZE);
        accountingStyle.setDataFormat(createCurrencyRateFormat(workbook));
        accountingStyle.setFont(accountingFont);

        return accountingStyle;
    }

    public static CellStyle createDifferencesStyle(Workbook workbook){
        CellStyle diffStyle = workbook.createCellStyle();
        Font diffFont = workbook.createFont();
        DataFormat diffFormat = workbook.createDataFormat();

        diffFont.setFontName(FONT);
        diffFont.setFontHeightInPoints(FONT_SIZE);
        diffFont.setColor(IndexedColors.RED.getIndex());
        diffStyle.setDataFormat(createAccountingFormat(workbook));
        diffStyle.setFont(diffFont);
        diffStyle.setFillPattern(FillPatternType.NO_FILL);

        return diffStyle;
    }

    public static CellStyle createClosingRowStyle(Workbook workbook){
        CellStyle closingStyle = workbook.createCellStyle();

        closingStyle.setBorderTop(BorderStyle.THIN);

        return closingStyle;
    }

    public static CellStyle createDelimiterRowStyle(Workbook workbook){
        CellStyle delimiterRowStyle = workbook.createCellStyle();
        Font delimiterRowFont = workbook.createFont();

        delimiterRowFont.setFontName(FONT);
        delimiterRowFont.setFontHeightInPoints(FONT_SIZE);
        delimiterRowFont.setBold(true);
        delimiterRowFont.setItalic(true);

        delimiterRowStyle.setFont(delimiterRowFont);
        delimiterRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        delimiterRowStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        return delimiterRowStyle;
    }

    public static CellStyle createLeftBorderedCellStyle(Workbook workbook){
        CellStyle leftBorderedCellStyle = workbook.createCellStyle();
        Font leftBorderedCellFont = workbook.createFont();

        leftBorderedCellFont.setFontName(FONT);
        leftBorderedCellFont.setFontHeightInPoints(FONT_SIZE);
        leftBorderedCellFont.setBold(true);

        leftBorderedCellStyle.setFont(leftBorderedCellFont);
        leftBorderedCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        leftBorderedCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        leftBorderedCellStyle.setBorderTop(BorderStyle.MEDIUM);
        leftBorderedCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        leftBorderedCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        return leftBorderedCellStyle;
    }

    public static CellStyle createRightBorderedCellStyle(Workbook workbook){
        CellStyle rightBorderedCellStyle = workbook.createCellStyle();
        Font rightBorderedCellFont = workbook.createFont();

        rightBorderedCellFont.setFontName(FONT);
        rightBorderedCellFont.setFontHeightInPoints(FONT_SIZE);
        rightBorderedCellStyle.setFont(rightBorderedCellFont);
        rightBorderedCellStyle.setBorderTop(BorderStyle.MEDIUM);
        rightBorderedCellStyle.setBorderRight(BorderStyle.MEDIUM);
        rightBorderedCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        rightBorderedCellStyle.setDataFormat(createAccountingFormat(workbook));

        return rightBorderedCellStyle;
    }

    public static CellStyle createCheckLineCellStyle(Workbook workbook){
        CellStyle checkLineCellStyle = workbook.createCellStyle();
        Font checkLineCellFont = workbook.createFont();

        checkLineCellFont.setFontName(FONT);
        checkLineCellFont.setFontHeightInPoints(FONT_SIZE);
        checkLineCellFont.setColor(IndexedColors.RED.getIndex());
        checkLineCellStyle.setFont(checkLineCellFont);
        checkLineCellStyle.setBorderTop(BorderStyle.THIN);
        checkLineCellStyle.setFillPattern(FillPatternType.NO_FILL);
        checkLineCellStyle.setDataFormat(createAccountingFormat(workbook));
        return checkLineCellStyle;
    }

    public static CellStyle createDateCellStyle(Workbook workbook){
        CellStyle dateCellStyle = workbook.createCellStyle();
        Font dateCellFont = workbook.createFont();

        dateCellFont.setFontName(FONT);
        dateCellFont.setFontHeightInPoints(FONT_SIZE);
        dateCellStyle.setFont(dateCellFont);
        dateCellStyle.setDataFormat(createDateFormat(workbook));
        return dateCellStyle;
    }

    public static short createDateFormat(Workbook workbook){
        DataFormat dateFormat = workbook.createDataFormat();
        return dateFormat.getFormat("m/d/yy");
    }

    public static short createAccountingFormat(Workbook workbook){
        DataFormat accountingDataFormat = workbook.createDataFormat();
        return accountingDataFormat.getFormat("#,##0_);-;(#,##0)");
    }
    public static short createCurrencyRateFormat(Workbook workbook){
        DataFormat accountingDataFormat = workbook.createDataFormat();
        return accountingDataFormat.getFormat("#.####");
    }

}
