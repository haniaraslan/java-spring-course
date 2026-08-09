public class CurrentAccount extends BankAccount {
    private double overdraftLimit = 1000;

    public CurrentAccount(String owner, double balance) {
        super(owner, balance, 2000);
    }

    @java.lang.Override
    public void withdraw(double amount) {
        ensureAccountActive();
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be positive");
        if (balance - amount < -overdraftLimit) throw new IllegalArgumentException("Overdraft limit exceeded");
        this.balance -= amount;
        this.successfulTransactions++;
    }

    public boolean isOverdraftUsed() {
        return balance < 0;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
