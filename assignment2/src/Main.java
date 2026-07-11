import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pin;
        int retries = 3;

        while (retries > 0) {
            System.out.println("Enter your PIN: ");
            pin = scanner.nextLine();
            if (pin.equals("1234"))
                break;
            else
                retries--;
        }
        if (retries == 0) {
            System.out.println("Your account has\n" +
                    "been locked.");
            return;
        }

        double balance = 2500.75f;
        int depositsCount = 0;
        int withdrawalsCount = 0;

        String choice;
        do {
            System.out.println("========= ATM =========\n" +
                    "1. Check Balance\n" +
                    "2. Deposit\n" +
                    "3. Withdraw\n" +
                    "4. Show Account Status\n" +
                    "5. Exit\n" +
                    "=======================");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.printf("You current balance is %.2f%n", balance);
                    break;
                case "2":
                    System.out.println("Please Enter the Amount:");
                    double depositAmount = scanner.nextFloat();
                    scanner.nextLine();
                    if (depositAmount < 0) {
                        System.out.println("Invalid Amount");
                    } else if (depositAmount == 0) {
                        System.out.println("Transaction Cancelled.");
                    } else {
                        balance += depositAmount;
                        depositsCount++;
                    }
                    System.out.printf("You current balance is %.2f%n", balance);

                    break;
                case "3":
                    System.out.println("Please Enter the Amount:");
                    double withdrawAmount = scanner.nextFloat();
                    scanner.nextLine();
                    if (withdrawAmount < 0) {
                        System.out.println("Invalid Amount.");
                    } else if (withdrawAmount == 0) {
                        System.out.println("Transaction cancelled.");
                    } else if (withdrawAmount > balance) {
                        System.out.println("Insufficient balance.");
                    } else {
                        balance -= withdrawAmount;
                        withdrawalsCount++;
                        if (balance == 0) {
                            System.out.println("Warning: Your account is empty.");
                        }
                        System.out.printf("You current balance is %.2f%n", balance);
                    }
                    break;
                case "4":
                    if (balance >= 5000) {
                        System.out.println("VIP Customer");
                    } else if (balance >= 1000 && balance <= 4999.99) {
                        System.out.println("Regular Customer");
                    } else if (balance < 1000) {
                        System.out.println("Low Balance");
                    }
                    break;
                case "5":
                    System.out.println("Thank you for using our ATM.");
                    System.out.println("Transactions Summary: \nDeposits: " + depositsCount + " \t Withdrawals: " + withdrawalsCount);

                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }

        } while (!choice.equals("5"));

    }
}