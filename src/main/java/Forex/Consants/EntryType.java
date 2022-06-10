package Forex.Consants;

public enum EntryType {
    DR ("DR"),
    CR ("CR");

    private String entryType;

    EntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getEntryType() {
        return entryType;
    }

    @Override
    public String toString() {
        return entryType;
    }
}
