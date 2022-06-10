package ForexRecalculationTest;

import Forex.CalculationLogic.ForexRecalculation;
import Forex.Consants.EntryType;
import Forex.DataContainers.ForexDailyRecalculation;
import Forex.DataContainers.JERecord;
import Forex.DataContainers.NBURate;
import Forex.DataContainers.TBAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ForexRecalculationTest {

    private static List<NBURate> nbuRates;
    private static List<TBAccount> tbAccounts;
    private static List<JERecord> journalEntries;

    ForexRecalculation fxc;

    @BeforeAll
    public void setup(){

       NBURate nbuRate = new NBURate(new Date(2021, 1, 15), "", "840", "USD",
               "1", "дол.", BigDecimal.valueOf(24.9990));
       nbuRates = new ArrayList<>();
       nbuRates.add(nbuRate);

       tbAccounts = new ArrayList<>();
       TBAccount tbAccount = new TBAccount("1", "some", "USD", "840",
               new BigDecimal(10000), new BigDecimal(5000), new BigDecimal(-3000), new BigDecimal(12000));
       tbAccounts.add(tbAccount);

       journalEntries = new ArrayList<>();
       JERecord jeRecord1 = new JERecord(
               new Date(2021, 1, 15), "USD", "DR", BigDecimal.valueOf(1500),""
       );
        JERecord jeRecord2 = new JERecord(
                new Date(2021, 1, 15), "USD", "DR", BigDecimal.valueOf(1000),""
        );
        JERecord jeRecord3 = new JERecord(
                new Date(2021, 1, 16), "USD", "CR", BigDecimal.valueOf(-1000),""
        );
        JERecord jeRecord4 = new JERecord(
                new Date(2021, 1, 16), "USD", "CR", BigDecimal.valueOf(-3000),""
        );

        journalEntries.add(jeRecord1);
        journalEntries.add(jeRecord2);
        journalEntries.add(jeRecord3);
        journalEntries.add(jeRecord4);

       fxc = new ForexRecalculation(nbuRates, tbAccounts, journalEntries, 2021);

    }

    @Test
    public void testExtractCurrencyRateByDate(){


        BigDecimal result = fxc.extractCurrencyRateByDate("USD", new Date(2021, 1, 15));
        Assertions.assertEquals(result, BigDecimal.valueOf(24.9990));
    }

    @Test
    public void firstLineIsEmpty_testCalculateOpeningBalance() {

        ForexDailyRecalculation emptyLine = new ForexDailyRecalculation();

        BigDecimal result = fxc.calculateOpeningBalance("USD", emptyLine);
        Assertions.assertEquals(result, new BigDecimal(10000));

    }

    @Test
    public void firstLineIsNotEmpty_testCalculateOpeningBalance(){

        ForexDailyRecalculation notEmptyLine = new ForexDailyRecalculation(
                "USD", new Date(2021, 1, 15), BigDecimal.valueOf(25.00), BigDecimal.valueOf(2000),
                BigDecimal.valueOf(2000), BigDecimal.valueOf(-1000), BigDecimal.valueOf(3000), BigDecimal.valueOf(0));


        BigDecimal result = fxc.calculateOpeningBalance("USD", notEmptyLine);
        Assertions.assertEquals(result, BigDecimal.valueOf(3000));

    }

    @Test
    public void calculateDebitTurnoverTest(){
        BigDecimal result = fxc.calculateTurnoverByType("USD", new Date(2021, 1, 15), EntryType.DR);
        Assertions.assertEquals(result, BigDecimal.valueOf(2500.00));
    }

    @Test
    public void calculateCreditTurnoverTest(){
        BigDecimal result = fxc.calculateTurnoverByType("USD", new Date(2021, 1, 16), EntryType.CR);
        Assertions.assertEquals(result, BigDecimal.valueOf(-4000.00));
    }

    @Test
    public void calculateClosingBalance(){
        ForexDailyRecalculation forexDaily = new ForexDailyRecalculation("USD",
                new Date(2021, 3, 14), BigDecimal.valueOf(25.1010), BigDecimal.valueOf(10000),
                BigDecimal.valueOf(3000), BigDecimal.valueOf(-4000), BigDecimal.valueOf(0.0), BigDecimal.valueOf(0.0)
        );
        BigDecimal result = fxc.calculateClosingBalance(forexDaily);
        Assertions.assertEquals(BigDecimal.valueOf(9000), result);
    }

    @Test
    public void calculateProfitLossTest(){
        ForexDailyRecalculation currentLine = new ForexDailyRecalculation("USD",
                new Date(2021, 3, 14), BigDecimal.valueOf(25.0), BigDecimal.valueOf(10000),
                BigDecimal.valueOf(3000), BigDecimal.valueOf(-1000), BigDecimal.valueOf(12000), BigDecimal.valueOf(0.0)
        );
        ForexDailyRecalculation previousLine = new ForexDailyRecalculation("USD",
                new Date(2021, 3, 13), BigDecimal.valueOf(26.0), BigDecimal.valueOf(9000),
                BigDecimal.valueOf(1000), BigDecimal.valueOf(0), BigDecimal.valueOf(10000), BigDecimal.valueOf(0.0)
        );

        BigDecimal result = fxc.calculateProfitLoss(currentLine, previousLine);
        Assertions.assertEquals(BigDecimal.valueOf(-2000.0), result);
    }

    @Test
    public void extractOpeningBalanceFromTbIfAbsentThrowsException(){
        boolean thrown = false;
        try {
            BigDecimal result = fxc.extractOpeningBalanceFromTrialBalance("UAH");
        } catch (NoSuchElementException e) {
            thrown  = true;
        }

        Assertions.assertTrue(thrown);


    }

}
