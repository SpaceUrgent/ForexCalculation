package Forex.CalculationLogic;

import Forex.DataContainers.AccountReconciliation;
import Forex.DataContainers.JERecord;
import Forex.DataContainers.TBAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class Reconciliation {


    public ArrayList<AccountReconciliation> performReconciliation(ArrayList<JERecord> journalEntries, ArrayList<TBAccount> trialBalanceAccounts){

        ArrayList<AccountReconciliation> accountReconciliations = new ArrayList<>();
        Iterator<TBAccount> accountTbIterator = trialBalanceAccounts.iterator();

        while (accountTbIterator.hasNext()){
            TBAccount tbAccount = accountTbIterator.next();
            String accountId = tbAccount.getAccountId();
            String accountDescription = tbAccount.getAccountDescription();
            String accountCurrency = tbAccount.getCurrency();
            String currencyCode = tbAccount.getCurrencyCode();
            BigDecimal openingBalancePerTb = tbAccount.getOpeningBalance();
            BigDecimal debitTurnoverPerTb = tbAccount.getDebitTurnover();
            BigDecimal creditTurnoverPerTb = tbAccount.getCreditTurnover();
            BigDecimal closingBalancePerTb = tbAccount.getClosingBalance();
            BigDecimal debitTurnoverPerJe = calculateDebitTurnoverPerJe(journalEntries, tbAccount);
            BigDecimal creditTurnoverPerJe = calculateCreditTurnoverPerJe(journalEntries, tbAccount);
            BigDecimal debitTurnoverDifference = calculateTurnoverDiff(debitTurnoverPerTb, debitTurnoverPerJe);
            BigDecimal creditTurnoverDifference = calculateTurnoverDiff(creditTurnoverPerTb, creditTurnoverPerJe);
            accountReconciliations.add(new AccountReconciliation(accountId, accountDescription, accountCurrency, currencyCode, openingBalancePerTb, debitTurnoverPerTb, creditTurnoverPerTb, closingBalancePerTb, debitTurnoverPerJe, creditTurnoverPerJe, debitTurnoverDifference, creditTurnoverDifference));
        }
        System.out.println("RESULT");
        System.out.println("__________________________");
        for (AccountReconciliation it:accountReconciliations) {
            System.out.println(it);
        }

        return accountReconciliations;
    }

    private BigDecimal calculateTurnoverDiff(BigDecimal debitTurnoverPerTb, BigDecimal debitTurnoverPerJe) {
        return debitTurnoverPerJe.subtract(debitTurnoverPerTb);
    }

    private BigDecimal calculateCreditTurnoverPerJe(ArrayList<JERecord> journalEntries, TBAccount tbAccount) {
        return BigDecimal.valueOf(journalEntries.stream()
                .filter(je -> je.getAccountId().equals(tbAccount.getAccountId()))
                .filter(je -> je.getEntryType().equals("CR"))
                .mapToDouble(je -> je.getAmount().doubleValue())
                .sum());
    }

    private BigDecimal calculateDebitTurnoverPerJe(ArrayList<JERecord> journalEntries, TBAccount tbAccount) {
        return BigDecimal.valueOf(journalEntries.stream()
                .filter(je -> je.getAccountId().equals(tbAccount.getAccountId()))
                .filter(je -> je.getEntryType().equals("DR"))
                .mapToDouble(je -> je.getAmount().doubleValue())
                .sum());
    }

    public Reconciliation() {
    }
}
