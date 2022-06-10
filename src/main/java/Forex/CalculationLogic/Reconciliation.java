package Forex.CalculationLogic;

import Forex.Consants.EntryType;
import Forex.DataContainers.AccountReconciliation;
import Forex.DataContainers.JERecord;
import Forex.DataContainers.TBAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class Reconciliation {


    public ArrayList<AccountReconciliation> performReconciliation(ArrayList<JERecord> journalEntries, ArrayList<TBAccount> trialBalanceAccounts){

        ArrayList<AccountReconciliation> accountReconciliations = new ArrayList<>();

        for (TBAccount iterator : trialBalanceAccounts){
            TBAccount tbAccount = iterator;
            String accountId = tbAccount.getAccountId();
            String accountDescription = tbAccount.getAccountDescription();
            String accountCurrency = tbAccount.getCurrency();
            String currencyCode = tbAccount.getCurrencyCode();
            BigDecimal openingBalancePerTb = tbAccount.getOpeningBalance();
            BigDecimal debitTurnoverPerTb = tbAccount.getDebitTurnover();
            BigDecimal creditTurnoverPerTb = tbAccount.getCreditTurnover();
            BigDecimal closingBalancePerTb = tbAccount.getClosingBalance();
            BigDecimal debitTurnoverPerJe = calculateTurnoverByType(journalEntries, tbAccount, EntryType.DR);
            BigDecimal creditTurnoverPerJe = calculateTurnoverByType(journalEntries, tbAccount, EntryType.CR);
            BigDecimal debitTurnoverDifference = calculateTurnoverDiff(debitTurnoverPerTb, debitTurnoverPerJe);
            BigDecimal creditTurnoverDifference = calculateTurnoverDiff(creditTurnoverPerTb, creditTurnoverPerJe);
//            accountReconciliations.add(new AccountReconciliation(accountId, accountDescription, accountCurrency, currencyCode, openingBalancePerTb, debitTurnoverPerTb, creditTurnoverPerTb, closingBalancePerTb, debitTurnoverPerJe, creditTurnoverPerJe, debitTurnoverDifference, creditTurnoverDifference));
            accountReconciliations.add(
                    new AccountReconciliation.AccountReconciliationBuilder()
                            .accountId(accountId)
                            .accountDescription(accountDescription)
                            .accountCurrency(accountCurrency)
                            .currencyCode(currencyCode)
                            .openingBalancePerTb(openingBalancePerTb)
                            .debitTurnoverPerTb(debitTurnoverPerTb)
                            .creditTurnoverPerTb(creditTurnoverPerTb)
                            .closingBalancePerTb(closingBalancePerTb)
                            .debitTurnoverPerJe(debitTurnoverPerJe)
                            .creditTurnoverPerJe(creditTurnoverPerJe)
                            .debitTurnoverDifference(debitTurnoverDifference)
                            .creditTurnoverDifference(creditTurnoverDifference)
                            .build()
            );
        }
//        System.out.println("RESULT");
//        System.out.println("__________________________");
//        for (AccountReconciliation it:accountReconciliations) {
//            System.out.println(it);
//        }

        return accountReconciliations;
    }

    private BigDecimal calculateTurnoverDiff(BigDecimal debitTurnoverPerTb, BigDecimal debitTurnoverPerJe) {
        return debitTurnoverPerJe.subtract(debitTurnoverPerTb);
    }

    private BigDecimal calculateTurnoverByType(ArrayList<JERecord> journalEntries, TBAccount tbAccount, EntryType entryType) {
        return BigDecimal.valueOf(journalEntries.stream()
                .filter(je -> je.getAccountId().equals(tbAccount.getAccountId()))
                .filter(je -> je.getEntryType().equals(entryType.toString()))
                .mapToDouble(je -> je.getAmount().doubleValue())
                .sum());
    }

    public Reconciliation() {
    }
}
