package Forex.DataContainers;

import java.math.BigDecimal;
import java.util.Date;

public class JERecord {

    Date recordDate;
    String currency;
    String entryType;
    BigDecimal amount;

    public JERecord(Date recordDate, String currency, String entryType, BigDecimal amount) {
        this.recordDate = recordDate;
        this.currency = currency;
        this.entryType = entryType;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "JERecord{" + "recordDate=" + recordDate + ", currency='" + currency + '\'' + ", entryType='" + entryType + '\'' + ", amount=" + amount + '}';
    }
}
