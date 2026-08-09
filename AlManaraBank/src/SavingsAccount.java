
public class SavingsAccount extends BankAccount {
    private long successfulWithdrawals = 0;
    private float interestRate = 0.15f;

    public SavingsAccount(String owner, double balance) {
        super(owner, balance, 1000);
    }

    @java.lang.Override
    public void withdraw(double amount) {
        ensureAccountActive();
        if (amount <= 0)
            throw new IllegalArgumentException("Withdrawal must be positive");
        if (amount > this.balance)
            throw new IllegalArgumentException("Insufficient funds: Savings cannot go negative");
        this.balance -= amount;
        successfulWithdrawals++;
        this.successfulTransactions++;
    }

    public long getSuccessfulWithdrawals() {
        return successfulWithdrawals;
    }

    public float getInterestRate() {
        return interestRate;
    }
}
