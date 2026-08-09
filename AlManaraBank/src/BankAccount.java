

public abstract class BankAccount {
    protected double balance = 0;
    private final String accountNumber;
    private final String owner;
    private AccountStatus status;
    protected float minAmount;
    protected int successfulTransactions = 0;

    public BankAccount(String owner, double balance, float minAmount) {
        this.minAmount = minAmount;
        if (balance < minAmount)
            throw new IllegalArgumentException("Minimum balance requirements not reached, minimum amount: " + this.minAmount);
        this.balance = balance;
        this.accountNumber = AccountNumberGenerator.generate();
        this.owner = owner;
        this.status = AccountStatus.ACTIVE;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public AccountStatus getAccountStatus() {
        return status;
    }

    public void setAccountStatus(AccountStatus status) {
        this.status = status;
    }

    protected void ensureAccountActive() {
        if (getAccountStatus() == AccountStatus.FROZEN || getAccountStatus() == AccountStatus.FROZEN)
            throw new IllegalArgumentException("Account is " + status);
    }

    public abstract void withdraw(double amount);

    public void deposit(double amount) {
        ensureAccountActive();
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");
        this.balance += amount;
        this.successfulTransactions++;
    }

    public void closeAccount() {
        this.status = AccountStatus.CLOSED;
    }

    public int getSuccessfulTransactions() {
        return successfulTransactions;
    }

    public void printAccountDetails() {
        System.out.println("Account Number: " + this.accountNumber);
        System.out.println("Owner: " + this.owner);
        System.out.println("Balance: " + this.balance);
        System.out.println("Status: " + this.status);
        if (successfulTransactions > 0)
            System.out.println("Transactions: " + this.successfulTransactions);
    }

}
