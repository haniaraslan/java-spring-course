import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FixedDepositAccount extends BankAccount {
    private final LocalDate maturityDate;
    private final LocalDate startDate;
    private float interestRate = 0.17f;
    private int duration;


    public FixedDepositAccount(java.lang.String owner, double balance, int duration) {
        super(owner, balance, 500);
        this.duration = duration;
        startDate = LocalDate.now();
        this.maturityDate = startDate.plusMonths(duration);
    }

    @java.lang.Override
    public void withdraw(double amount) {
        ensureAccountActive();
        if (!isMatured())
            throw new IllegalArgumentException("Account has not reached maturity! Months Remaining : " + getMonthsRemaining());
        if (amount <= 0)
            throw new IllegalArgumentException("Withdrawal must be positive");
        if (amount > balance)
            throw new IllegalArgumentException("Insufficient funds");
        balance -= amount;
        this.successfulTransactions++;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public int getDuration() {
        return duration;
    }

    public long getMonthsPassed() {
        return ChronoUnit.MONTHS.between(startDate, LocalDate.now());
    }

    public long getMonthsRemaining() {
        long remaining = duration - getMonthsPassed();
        return Math.max(remaining, 0);
    }

    public boolean isMatured() {
        return !LocalDate.now().isBefore(maturityDate);
    }
}
