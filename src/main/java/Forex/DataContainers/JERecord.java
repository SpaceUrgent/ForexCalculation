package Forex.DataContainers;

import java.math.BigDecimal;
import java.util.Date;

public class JERecord {

    Date recordDate;
    String currency;
    String entryType;
    BigDecimal amount;

    String accountId;

    public JERecord(Date recordDate, String currency, String entryType, BigDecimal amount, String accountId) {
        this.recordDate = recordDate;
        this.currency = currency;
        this.entryType = entryType;
        this.amount = amount;
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "JERecord{" + "recordDate=" + recordDate + ", currency='" + currency + '\'' + ", entryType='" + entryType + '\'' + ", amount=" + amount + ", account=" + accountId +'}';
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public String getCurrency() {
        return currency;
    }

    public String getEntryType() {
        return entryType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAccountId() {
        return accountId;
    }
}
