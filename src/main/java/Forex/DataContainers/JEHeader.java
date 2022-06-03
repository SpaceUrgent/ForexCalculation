package Forex.DataContainers;

public class JEHeader {

    String date;
    String currency;
    String entry;
    String amount;

    public JEHeader(String date, String currency, String entry, String amount) {
        this.date = date;
        this.currency = currency;
        this.entry = entry;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "JEHeader{" + "date='" + date + '\'' + ", currency='" + currency + '\'' + ", entry='" + entry + '\'' + ", amount='" + amount + '\'' + '}';
    }
}
