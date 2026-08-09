import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static HashMap<String, Customer> customers = new HashMap<>();
    static HashMap<String, BankAccount> accounts = new HashMap<>();

    private static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) return true; // optional
        if (!phone.matches("\\d+")) return false;
        return phone.length() >= 7 && phone.length() <= 15;
    }

    private static boolean nationalIdExists(String nationalId) {
        return customers.containsKey(nationalId);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("========= Welcome to Al Manara Bank =========\n" +
                    "1. Register New Customer\n" +
                    "2. Open New Account\n" +
                    "3. Deposit Money\n" +
                    "4. Withdraw Money\n" +
                    "5. Display Customer Accounts\n" +
                    "6. Display All Branch Accounts\n" +
                    "7. Search Account by Number\n" +
                    "8. Search Accounts by Type\n" +
                    "9. Close an Account\n" +
                    "0. Exit\n" +
                    "=======================");
            choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    System.out.println("Enter Full Name:");
                    String fullName = scanner.nextLine().trim();

                    if (fullName.isEmpty()) {
                        System.out.println("Error: Name cannot be empty.");
                        break;
                    }

                    System.out.println("Enter National ID:");
                    String nationalId = scanner.nextLine().trim();

                    if (nationalIdExists(nationalId)) {
                        System.out.println("Error: National ID already registered.");
                        break;
                    }

                    System.out.println("Enter Phone Number (optional):");
                    String phone = scanner.nextLine().trim();
                    if (!isValidPhone(phone)) {
                        System.out.println("Error: Phone number must be digits only (7–15 digits).");
                        break;
                    }

                    System.out.println("Enter Tier number \n" +
                            "1.Standard\n" +
                            "2.Silver\n" +
                            "3.Gold):");
                    String tierChoice = scanner.nextLine().trim();
                    CustomerTier tier = CustomerTier.UNKNOWN;
                    switch (tierChoice) {
                        case "1":
                            tier = CustomerTier.STANDARD;
                            break;
                        case "2":
                            tier = CustomerTier.SILVER;
                            break;
                        case "3":
                            tier = CustomerTier.GOLD;
                            break;
                        default:
                            tier = CustomerTier.UNKNOWN;
                            break;
                    }
                    if (tier == CustomerTier.UNKNOWN) {
                        System.out.println("Invalid Customer tier.");
                        break;
                    }
                    Customer newCustomer = new Customer(fullName, nationalId, phone.isEmpty() ? null : phone, tier);
                    customers.put(nationalId, newCustomer);

                    System.out.println("Customer registered successfully!");
                    newCustomer.printDetails();
                    break;
                }
                case "2": {
                    System.out.println("Enter Customer ID:");
                    int customerId = Integer.parseInt(scanner.nextLine().trim());

                    Customer customer = customers.get(customerId);

                    if (customer == null) {
                        System.out.println("Error: Customer not found. Cannot open account.");
                        break;
                    }

                    System.out.println("Select Account Type: \n + " +
                            "1. Savings\n" +
                            "2. Current\n" +
                            "3. Fixed Deposit");

                    String typeChoice = scanner.nextLine().trim();
                    AccountType type = AccountType.UNKNOWN;

                    switch (typeChoice) {
                        case "1":
                            type = AccountType.SAVINGS;
                            break;
                        case "2":
                            type = AccountType.CURRENT;
                            break;
                        case "3":
                            type = AccountType.FIXED_DEPOSIT;
                            break;
                        default:
                            type = AccountType.UNKNOWN;
                            break;
                    }

                    if (type == AccountType.UNKNOWN) {
                        System.out.println("Invalid account type.");
                        break;
                    }

                    System.out.println("Enter Opening Balance:");
                    double openingBalance = Double.parseDouble(scanner.nextLine());

                    if (openingBalance < type.getMinOpeningBalance()) {
                        System.out.println("Error: Opening balance must be at least " + type.getMinOpeningBalance());
                        break;
                    }

                    BankAccount newAccount;

                    if (type == AccountType.SAVINGS) {
                        newAccount = new SavingsAccount(customer.getFullname(), openingBalance);
                    } else if (type == AccountType.CURRENT) {
                        newAccount = new CurrentAccount(customer.getFullname(), openingBalance);
                    } else {
                        System.out.println("Enter duration in months:");
                        int duration = Integer.parseInt(scanner.nextLine());
                        newAccount = new FixedDepositAccount(customer.getFullname(), openingBalance, duration);
                    }

                    accounts.put(newAccount.getAccountNumber(), newAccount);
                    customer.addAccount(newAccount);

                    System.out.println("Account created successfully!");
                    newAccount.printAccountDetails();
                    break;
                }
                case "3": {
                    System.out.println("Enter Account Number:");
                    String accountNumber = scanner.nextLine().trim();

                    BankAccount account = accounts.get(accountNumber);

                    if (account == null) {
                        System.out.println("Error: Account not found.");
                        break;
                    }

                    try {
                        account.ensureAccountActive();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage() + ". Cannot deposit.");
                    }

                    System.out.println("Enter Deposit Amount:");
                    double amount;

                    try {
                        amount = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid amount.");
                        break;
                    }

                    try {
                        account.deposit(amount);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.println("Deposit successful!");
                    System.out.println("New Balance: " + account.getBalance());
                    break;
                }
                case "4": {
                    System.out.println("Enter Account Number:");
                    String accountNumber = scanner.nextLine().trim();

                    BankAccount account = accounts.get(accountNumber);

                    if (account == null) {
                        System.out.println("Error: Account not found.");
                        break;
                    }

                    try {
                        account.ensureAccountActive();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage() + ". Cannot deposit.");
                    }

                    System.out.println("Enter Withdrawal Amount:");
                    double amount;

                    try {
                        amount = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid amount.");
                        break;
                    }

                    if (amount <= 0) {
                        System.out.println("Error: Minimum withdrawal amount is 1");
                        break;
                    }

                    try {
                        account.withdraw(amount);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.println("Withdrawal successful!");
                    System.out.println("New Balance: " + account.getBalance());
                    break;
                }

                case "5": {
                    System.out.println("Enter Source Account Number:");
                    String sourceNumber = scanner.nextLine().trim();

                    BankAccount source = accounts.get(sourceNumber);

                    if (source == null) {
                        System.out.println("Error: Source account not found.");
                        break;
                    }

                    System.out.println("Enter Destination Account Number:");
                    String destNumber = scanner.nextLine().trim();

                    BankAccount destination = accounts.get(destNumber);

                    if (destination == null) {
                        System.out.println("Error: Destination account not found.");
                        break;
                    }

                    if (sourceNumber.equals(destNumber)) {
                        System.out.println("Error: Source and destination accounts must be different.");
                        break;
                    }

                    System.out.println("Enter Transfer Amount:");
                    double amount;

                    try {
                        amount = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid amount.");
                        break;
                    }

                    if (amount <= 0) {
                        System.out.println("Error: Transfer amount must be positive.");
                        break;
                    }

                    double originalSourceBalance = source.getBalance();

                    try {
                        source.withdraw(amount);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: Withdrawal failed — " + e.getMessage());
                        break;
                    }

                    try {
                        destination.deposit(amount);
                    } catch (IllegalArgumentException e) {
                        source.setBalance(originalSourceBalance);
                        System.out.println("Error: Deposit failed — " + e.getMessage());
                        System.out.println("Transfer cancelled. Source balance restored.");
                        break;
                    }

                    System.out.println("Transfer successful!");
                    System.out.println("Source New Balance: " + source.getBalance());
                    System.out.println("Destination New Balance: " + destination.getBalance());
                    break;
                }
                case "6": {
                    System.out.println("Enter Customer ID:");
                    String customerId = scanner.nextLine().trim();

                    Customer customer = customers.get(customerId);

                    if (customer == null) {
                        System.out.println("Error: Customer not found.");
                        break;
                    }

                    System.out.println("===== Customer Information =====");
                    customer.printDetails();

                    System.out.println("\n===== Accounts =====");

                    var customerAccounts = customer.getAccounts();

                    if (customerAccounts.isEmpty()) {
                        System.out.println("This customer has no accounts.");
                        break;
                    }

                    double totalBalance = 0;

                    for (BankAccount acc : customerAccounts) {
                        System.out.println("------------------------------");
                        acc.printAccountDetails();
                        totalBalance += acc.getBalance();
                    }

                    System.out.println("------------------------------");
                    System.out.println("Combined Balance: " + totalBalance);
                    break;
                }
                case "7": {
                    System.out.println("===== All Branch Accounts =====");

                    if (accounts.isEmpty()) {
                        System.out.println("No accounts found in this branch.");
                        break;
                    }

                    for (BankAccount acc : accounts.values()) {
                        System.out.println("------------------------------");
                        acc.printAccountDetails();
                        System.out.println("Account Type: " + acc.getClass().getSimpleName());
                    }

                    System.out.println("------------------------------");
                    System.out.println("Total Accounts: " + accounts.size());
                    break;
                }
                case "8": {
                    System.out.println("Enter Account Number:");
                    String inputNumber = scanner.nextLine().trim();

                    BankAccount found = null;

                    for (BankAccount acc : accounts.values()) {
                        if (acc.getAccountNumber().equalsIgnoreCase(inputNumber)) {
                            found = acc;
                            break;
                        }
                    }

                    if (found == null) {
                        System.out.println("Error: Account not found.");
                        break;
                    }

                    System.out.println("===== Account Information =====");
                    found.printAccountDetails();
                    System.out.println("Account Type: " + found.getClass().getSimpleName());

                    System.out.println("\n===== Type-Specific Details =====");

                    if (found instanceof SavingsAccount sa) {
                        System.out.println("Interest Rate: " + sa.getInterestRate() * 100 + "%");
                        System.out.println("Successful Withdrawals: " + sa.getSuccessfulWithdrawals());
                    } else if (found instanceof CurrentAccount ca) {
                        System.out.println("Overdraft Limit: " + ca.getOverdraftLimit());
                        System.out.println("Overdraft Used: " + (ca.isOverdraftUsed() ? "Yes" : "No"));
                    } else if (found instanceof FixedDepositAccount fd) {
                        System.out.println("Duration (Months): " + fd.getDuration());
                        System.out.println("Start Date: " + fd.getStartDate());
                        System.out.println("Maturity Date: " + fd.getMaturityDate());
                        System.out.println("Months Passed: " + fd.getMonthsPassed());
                        System.out.println("Months Remaining: " + fd.getMonthsRemaining());
                        System.out.println("Matured: " + (fd.isMatured() ? "Yes" : "No"));
                    }

                    break;
                }
                case "9": {
                    System.out.println("Select Account Type to Search:");
                    System.out.println("1. Savings");
                    System.out.println("2. Current");
                    System.out.println("3. Fixed Deposit");

                    String choiceType = scanner.nextLine().trim();
                    Class<?> selectedType = null;

                    switch (choiceType) {
                        case "1":
                            selectedType = SavingsAccount.class;
                            break;
                        case "2":
                            selectedType = CurrentAccount.class;
                            break;
                        case "3":
                            selectedType = FixedDepositAccount.class;
                            break;
                        default:
                            break;
                    }

                    if (selectedType == null) {
                        System.out.println("Invalid account type.");
                        break;
                    }

                    System.out.println("===== Accounts of Type: " + selectedType.getSimpleName() + " =====");

                    int count = 0;
                    double totalBalance = 0;

                    for (BankAccount acc : accounts.values()) {
                        if (selectedType.isInstance(acc)) {
                            System.out.println("------------------------------");
                            acc.printAccountDetails();
                            System.out.println("Account Type: " + acc.getClass().getSimpleName());
                            count++;
                            totalBalance += acc.getBalance();
                        }
                    }

                    if (count == 0) {
                        System.out.println("No accounts found for this type.");
                        break;
                    }

                    System.out.println("------------------------------");
                    System.out.println("Matching Accounts: " + count);
                    System.out.println("Combined Balance: " + totalBalance);
                    break;
                }
                case "10": {
                    System.out.println("Enter Account Number:");
                    String accNumber = scanner.nextLine().trim();

                    BankAccount account = accounts.get(accNumber);

                    if (account == null) {
                        System.out.println("Error: Account not found.");
                        break;
                    }

                    if (account.getAccountStatus() == AccountStatus.CLOSED) {
                        System.out.println("Error: Account is already closed.");
                        break;
                    }

                    if (account.getBalance() != 0) {
                        System.out.println("Error: Account balance must be exactly $0 to close.");
                        break;
                    }

                    if (account instanceof FixedDepositAccount fd) {
                        if (!fd.isMatured()) {
                            System.out.println("Error: Fixed Deposit account is still locked. Months remaining: " + fd.getMonthsRemaining());
                            break;
                        }
                    }

                    account.closeAccount();

                    Customer owner = null;

                    for (Customer c : customers.values()) {
                        if (c.getAccounts().contains(account)) {
                            owner = c;
                            break;
                        }
                    }

                    if (owner != null) {
                        owner.getAccounts().remove(account);
                    }

                    System.out.println("Account closed successfully!");
                    System.out.println("Account Number: " + account.getAccountNumber());
                    System.out.println("Status: " + account.getAccountStatus());
                    break;
                }
                case "0": {
                    System.out.println("Thank you for using Al Manara Bank System.");
                    System.out.println("Goodbye!");
                    break;
                }
            }
        } while (!choice.equals("0"));
    }
}