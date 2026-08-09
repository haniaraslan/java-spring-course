public enum AccountType {
    SAVINGS(1000),
    CURRENT(2000),
    FIXED_DEPOSIT(500),
    UNKNOWN(-1);

    private final double minOpeningBalance;

    AccountType(double minOpeningBalance) {
        this.minOpeningBalance = minOpeningBalance;
    }

    public double getMinOpeningBalance() {
        return minOpeningBalance;
    }
}
