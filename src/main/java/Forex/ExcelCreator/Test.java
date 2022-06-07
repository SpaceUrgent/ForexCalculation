package Forex.ExcelCreator;

import Forex.DataContainers.AccountReconciliation;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {

        AccountReconciliation accountReconciliation1 = new AccountReconciliation(
                "1001", "some text", "USD", "840",
                BigDecimal.valueOf(10000), BigDecimal.valueOf(5000), BigDecimal.valueOf(-4000), BigDecimal.valueOf(9000),
                BigDecimal.valueOf(5000), BigDecimal.valueOf(-4000), BigDecimal.valueOf(0), BigDecimal.valueOf(0));

        AccountReconciliation accountReconciliation2 = new AccountReconciliation(
                "1002", "some text", "EUR", "111",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(5000), BigDecimal.valueOf(-1000), BigDecimal.valueOf(5000),
                BigDecimal.valueOf(5000), BigDecimal.valueOf(-1000), BigDecimal.valueOf(0), BigDecimal.valueOf(0));

        AccountReconciliation accountReconciliation3 = new AccountReconciliation(
                "1003", "some text", "CHZ", "000",
                BigDecimal.valueOf(0), BigDecimal.valueOf(5000), BigDecimal.valueOf(-5000), BigDecimal.valueOf(0),
                BigDecimal.valueOf(5000), BigDecimal.valueOf(-5000), BigDecimal.valueOf(0), BigDecimal.valueOf(0));

        List<AccountReconciliation> accountReconciliations = new ArrayList<>();
        accountReconciliations.add(accountReconciliation1);
        accountReconciliations.add(accountReconciliation2);
        accountReconciliations.add(accountReconciliation3);

        String path = "C:\\Users\\Andrey\\Desktop\\output\\file.xlsx";

        ExcelCreator excelCreator = new ExcelCreator(path);
        excelCreator.createForexFile();
        excelCreator.fillReconciliationTable(accountReconciliations);




    }
}
